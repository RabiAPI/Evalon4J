package com.evalon4j.visitors

import com.evalon4j.java.JavaComponent
import com.evalon4j.java.JavaField
import com.evalon4j.java.JavaMethod
import com.evalon4j.java.JavaProject
import com.evalon4j.java.types.JavaAbstractType
import com.evalon4j.java.types.JavaEnumValue
import com.evalon4j.java.types.JavaGenericType
import com.evalon4j.java.types.JavaReferenceType
import com.evalon4j.json.JsonError
import com.github.javaparser.ast.ImportDeclaration
import com.github.javaparser.ast.PackageDeclaration
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration
import com.github.javaparser.ast.body.EnumDeclaration
import com.github.javaparser.ast.body.FieldDeclaration
import com.github.javaparser.ast.body.MethodDeclaration
import com.github.javaparser.ast.expr.LiteralStringValueExpr
import com.github.javaparser.ast.visitor.VoidVisitorAdapter

class JavaReferenceVisitor extends VoidVisitorAdapter<JavaProject> {
    private String moduleName = ""

    private String packageName = ""

    private List<String> imports = []

    private List<String> typePlaceholders = []

    private List<JsonError> errors = []

    JavaReferenceVisitor() {

    }

    JavaReferenceVisitor(String moduleName) {
        this.moduleName = moduleName
    }

    JavaReferenceVisitor(String moduleName, String packageName, List<String> imports) {
        this.moduleName = moduleName

        this.packageName = packageName

        this.imports = imports
    }

    @Override
    void visit(PackageDeclaration n, JavaProject arg) {
        if (!n.nameAsString) { // 跳过不存在包名的类
            return
        }

        this.packageName = n.nameAsString

        super.visit(n, this.packageName)
    }

    @Override
    void visit(ImportDeclaration n, JavaProject arg) {
        imports.add(n.nameAsString + (n.isAsterisk() ? ".*" : ""))

        super.visit(n, packageName)
    }

    @Override
    void visit(EnumDeclaration n, JavaProject arg) {
        if (!packageName) {
            return
        }

        def referenceType = new JavaReferenceType()

        referenceType.isEnum = true

        referenceType.moduleName = moduleName

        referenceType.simpleName = n.nameAsString

        referenceType.qualifiedName = packageName + "." + n.nameAsString

        referenceType.packageName = packageName

        referenceType.imports = imports

        referenceType.enumValues = n.entries.collect {
            def value = new JavaEnumValue(name: it.nameAsString)

            if (it.arguments) {
                value.value = it.arguments.toString()
            }

            JavadocHelper.setJavadoc(value, it)

            return value
        }

        JavadocHelper.setJavadoc(referenceType, n)

        arg.references << referenceType
    }

    @Override
    void visit(ClassOrInterfaceDeclaration n, JavaProject arg) {
        if (n.isInterface()) {
            return
        }

        typePlaceholders = n.typeParameters.collect {
            it.nameAsString
        }

        def referenceType = new JavaReferenceType()

        referenceType.moduleName = moduleName

        referenceType.simpleName = n.nameAsString

        referenceType.qualifiedName = packageName + "." + n.nameAsString

        referenceType.packageName = packageName

        referenceType.imports = imports

        if (n.isGeneric()) {
            referenceType.typePlaceholders = n.typeParameters.collect { it.nameAsString }
        }

        resolveExtensionTypes(n, referenceType)

        resolveInnerTypes(n, arg)

        n.fields.each { f ->
            if (f.isStatic()) {
                referenceType.staticFields << buildJavaField(f)
            } else {
                referenceType.fields << buildJavaField(f)
            }
        }

        def javaComponent = new JavaComponent(packageName: packageName, imports: imports, moduleName: moduleName)

        n.methods.each {m ->
            def javaMethod = new JavaMethod(referenceType)

            javaMethod.methodName = m.nameAsString

            JavadocHelper.setJavadoc(javaMethod, m)

            javaMethod.springAnnotations = new JavaAnnotationsBuilder(javaComponent).buildSpringAnnotations(m.annotations)

            javaMethod.jaxRSAnnotations = new JavaAnnotationsBuilder(javaComponent).buildJaxRSAnnotations(m.annotations)

            javaMethod.swaggerAnnotations = new JavaAnnotationsBuilder(javaComponent).buildSwaggerAnnotations(m.annotations)

            javaMethod.openAPIAnnotations = new JavaAnnotationsBuilder(javaComponent).buildOpenAPIAnnotations(m.annotations)

            buildMethodParameters(m, javaMethod, javaComponent)

            buildMethodResponse(m, javaMethod)

//            buildMethodExceptions(m, javaMethod)

            referenceType.methods << javaMethod
        }

        buildSwaggerAndOpenAPIDefinitions(n, arg)

        buildReferenceAnnotations(n, referenceType)

        arg.references << referenceType
    }

    void buildMethodParameters(MethodDeclaration n, JavaMethod javaMethod, JavaComponent javaComponent) {
        n.parameters.each { p ->
            try {
                def javaField = new JavaField()

                javaField.fieldName = p.nameAsString

                javaField.fieldType = new JavaAbstractTypeBuilder(moduleName, packageName, imports, typePlaceholders).buildFieldTypeRecursive(p.type)

                javaField.isRequired = JavaVisitorHelper.isRequired(p)

                javaField.javadocTitle = JavadocHelper.getParameterComment(n, p.nameAsString)

                javaField.springAnnotations = new JavaAnnotationsBuilder(javaComponent).buildSpringAnnotations(p.annotations)

                javaField.jaxRSAnnotations = new JavaAnnotationsBuilder(javaComponent).buildJaxRSAnnotations(p.annotations)

                javaField.swaggerAnnotations = new JavaAnnotationsBuilder(javaComponent).buildSwaggerAnnotations(p.annotations)

                javaField.openAPIAnnotations = new JavaAnnotationsBuilder(javaComponent).buildOpenAPIAnnotations(p.annotations)

                javaField.validationAnnotations = new JavaAnnotationsBuilder(javaComponent).buildValidationAnnotations(p.annotations)

                javaField.jacksonAnnotations = new JavaAnnotationsBuilder(javaComponent).buildJacksonAnnotations(p.annotations)

                javaMethod.parameters << javaField
            } catch (Exception e) {
                errors << new JsonError(e)
            }
        }
    }

    void buildMethodResponse(MethodDeclaration n, JavaMethod javaMethod) {
        try {
            def responseType = n.type

            // 对于返回 List<? extends XXX> 这种类型可以读取所有子类，作为可能的返回结果，当前版本先跳过

            if (responseType.isVoidType() || responseType.isWildcardType()) {
                return
            }

            def returnType = new JavaAbstractTypeBuilder(moduleName, packageName, imports, typePlaceholders).buildFieldTypeRecursive(responseType)

            returnType.javadocTitle = JavadocHelper.getReturnTypeComment(n)

            javaMethod.response = returnType
        } catch (Exception e) {
            errors << new JsonError(e)
        }
    }

    private buildSwaggerAndOpenAPIDefinitions(ClassOrInterfaceDeclaration n, JavaProject javaProject) {
        def javaComponent = new JavaComponent(packageName: packageName, imports: imports, moduleName: moduleName)

        def swaggerAnnotations = new JavaAnnotationsBuilder(javaComponent).buildSwaggerAnnotations(n.annotations)

        def openAPIAnnotations = new JavaAnnotationsBuilder(javaComponent).buildOpenAPIAnnotations(n.annotations)

        def javaModule = javaProject.modules.find {
            it.moduleName == moduleName
        }

        swaggerAnnotations?.swaggerDefinition && (javaModule.swaggerDefinition = swaggerAnnotations.swaggerDefinition)

        openAPIAnnotations?.openAPIDefinition && (javaModule.openAPIDefinition = openAPIAnnotations.openAPIDefinition)
    }

    private buildReferenceAnnotations(ClassOrInterfaceDeclaration n, JavaReferenceType referenceType) {
        def javaComponent = new JavaComponent(packageName: packageName, imports: imports, moduleName: moduleName)

        referenceType.swaggerAnnotations = new JavaAnnotationsBuilder(javaComponent).buildSwaggerAnnotations(n.annotations)

        referenceType.openAPIAnnotations = new JavaAnnotationsBuilder(javaComponent).buildOpenAPIAnnotations(n.annotations)

        referenceType.jacksonAnnotations = new JavaAnnotationsBuilder(javaComponent).buildJacksonAnnotations(n.annotations)
    }

    private buildJavaField(FieldDeclaration n) {
        def javaField = new JavaField()

        javaField.fieldName = getFieldName(n)

        javaField.fieldType = getFieldType(n)

        javaField.fieldValue = getFieldValue(n)

        JavadocHelper.setJavadoc(javaField, n)

        def javaComponent = new JavaComponent(packageName: packageName, imports: imports, moduleName: moduleName)

        javaField.swaggerAnnotations = new JavaAnnotationsBuilder(javaComponent).buildSwaggerAnnotations(n.annotations)

        javaField.openAPIAnnotations = new JavaAnnotationsBuilder(javaComponent).buildOpenAPIAnnotations(n.annotations)

        javaField.validationAnnotations = new JavaAnnotationsBuilder(javaComponent).buildValidationAnnotations(n.annotations)

        javaField.jacksonAnnotations = new JavaAnnotationsBuilder(javaComponent).buildJacksonAnnotations(n.annotations)

        return javaField
    }

    private String getFieldName(FieldDeclaration n) {
        return n.variables[0].nameAsString
    }

    private JavaAbstractType getFieldType(FieldDeclaration n) {
        return new JavaAbstractTypeBuilder(moduleName, packageName, imports, typePlaceholders).buildFieldTypeRecursive(n.elementType)
    }

    private String getFieldValue(FieldDeclaration n) { // 仅获取基本类型的初始值
        def optional = n.variables[0].initializer

        if (!optional.present) {
            return null
        }

        def expression = optional.get()

        if (expression instanceof LiteralStringValueExpr) {
            return expression.value
        }

        return null
    }

    private resolveExtensionTypes(ClassOrInterfaceDeclaration n, JavaReferenceType referenceType) {
        if (!n.extendedTypes) {
            return
        }

        def extendedType = new JavaAbstractTypeBuilder(moduleName, packageName, imports).buildFieldTypeRecursive(n.extendedTypes.first())

        if (extendedType instanceof JavaReferenceType || extendedType instanceof JavaGenericType) { // 排除非引用类型的继承
            JavaVisitorHelper.setQualifiedNameForJavaAbstractType(imports, packageName, extendedType)

            referenceType.extensions = [extendedType]
        }
    }

    private resolveInnerTypes(ClassOrInterfaceDeclaration n, JavaProject arg) {
        List<ClassOrInterfaceDeclaration> innerClasses = n.childNodes.findAll {
            it instanceof ClassOrInterfaceDeclaration
        } as List<ClassOrInterfaceDeclaration>

        innerClasses.each { ClassOrInterfaceDeclaration inner ->
            def javaClassVisitor = new JavaReferenceVisitor(moduleName, packageName, imports)

            javaClassVisitor.visit(inner, arg)
        }
    }
}
