package com.evalon4j.visitors


import com.evalon4j.java.*
import com.evalon4j.java.types.JavaReferenceType
import com.evalon4j.json.JsonError
import com.github.javaparser.ast.ImportDeclaration
import com.github.javaparser.ast.PackageDeclaration
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration
import com.github.javaparser.ast.body.MethodDeclaration
import com.github.javaparser.ast.visitor.VoidVisitorAdapter

class JavaVisitor extends VoidVisitorAdapter<JavaProject> {
    private String moduleName = ""

    private String packageName = ""

    private List<String> imports = []

    private List<String> typePlaceholders = []

    private List<JavaReferenceType> referenceTypes = []

    private List<JsonError> errors = []

    JavaVisitor(String moduleName, List<JavaReferenceType> referenceTypes) {
        this.moduleName = moduleName

        this.referenceTypes = referenceTypes
    }

    @Override
    void visit(PackageDeclaration n, JavaProject arg) {
        if (!n.nameAsString) { //  Ignore if no package name provided
            return
        }

        packageName = n.nameAsString

        super.visit(n, arg)
    }

    @Override
    void visit(ImportDeclaration n, JavaProject arg) {
        imports.add(n.nameAsString + (n.isAsterisk() ? ".*" : "")) // Add * Symbol

        super.visit(n, arg)
    }

    @Override
    void visit(ClassOrInterfaceDeclaration n, JavaProject arg) {
        JavaComponent javaComponent

        if (n.isInterface()) {
            javaComponent = new JavaInterface()

            arg.interfaces << javaComponent
        } else {
            javaComponent = new JavaClass()

            arg.classes << javaComponent
        }

        typePlaceholders = n.typeParameters.collect {
            it.nameAsString
        }

        javaComponent.moduleName = moduleName

        javaComponent.simpleName = n.nameAsString

        javaComponent.qualifiedName = packageName + "." + n.nameAsString

        javaComponent.packageName = packageName

        javaComponent.imports = imports

        javaComponent.springAnnotations = new JavaAnnotationsBuilder(javaComponent, referenceTypes).buildSpringAnnotations(n.annotations)

        javaComponent.jaxRSAnnotations = new JavaAnnotationsBuilder(javaComponent, referenceTypes).buildJaxRSAnnotations(n.annotations)

        javaComponent.swaggerAnnotations = new JavaAnnotationsBuilder(javaComponent, referenceTypes).buildSwaggerAnnotations(n.annotations)

        javaComponent.openAPIAnnotations = new JavaAnnotationsBuilder(javaComponent, referenceTypes).buildOpenAPIAnnotations(n.annotations)

        JavadocHelper.setJavadoc(javaComponent, n)

        n.methods.each { methodDeclaration ->
            try {
                def javaMethod = new JavaMethod(javaComponent)

                javaMethod.methodName = methodDeclaration.nameAsString

                JavadocHelper.setJavadoc(javaMethod, methodDeclaration)

                javaMethod.springAnnotations = new JavaAnnotationsBuilder(javaComponent, referenceTypes).buildSpringAnnotations(methodDeclaration.annotations)

                javaMethod.jaxRSAnnotations = new JavaAnnotationsBuilder(javaComponent, referenceTypes).buildJaxRSAnnotations(methodDeclaration.annotations)

                javaMethod.swaggerAnnotations = new JavaAnnotationsBuilder(javaComponent, referenceTypes).buildSwaggerAnnotations(methodDeclaration.annotations)

                javaMethod.openAPIAnnotations = new JavaAnnotationsBuilder(javaComponent, referenceTypes).buildOpenAPIAnnotations(methodDeclaration.annotations)

                buildMethodParameters(methodDeclaration, javaMethod, javaComponent)

                buildMethodResponse(methodDeclaration, javaMethod)

                buildMethodExceptions(methodDeclaration, javaMethod)

                javaComponent.methods << javaMethod
            } catch (Exception e) {
                errors << new JsonError(e)
            }
        }

        arg.errors.addAll(errors)
    }

    void buildMethodParameters(MethodDeclaration n, JavaMethod javaMethod, JavaComponent javaComponent) {
        n.parameters.each { p ->
            try {
                def javaField = new JavaField()

                javaField.fieldName = p.nameAsString

                javaField.fieldType = new JavaAbstractTypeBuilder(moduleName, packageName, imports, typePlaceholders).buildFieldTypeRecursive(p.type)

                javaField.isRequired = JavaVisitorHelper.isRequired(p)

                javaField.javadocTitle = JavadocHelper.getParameterComment(n, p.nameAsString)

                javaField.springAnnotations = new JavaAnnotationsBuilder(javaComponent, referenceTypes).buildSpringAnnotations(p.annotations)

                javaField.jaxRSAnnotations = new JavaAnnotationsBuilder(javaComponent, referenceTypes).buildJaxRSAnnotations(p.annotations)

                javaField.swaggerAnnotations = new JavaAnnotationsBuilder(javaComponent, referenceTypes).buildSwaggerAnnotations(p.annotations)

                javaField.openAPIAnnotations = new JavaAnnotationsBuilder(javaComponent, referenceTypes).buildOpenAPIAnnotations(p.annotations)

                javaField.validationAnnotations = new JavaAnnotationsBuilder(javaComponent, referenceTypes).buildValidationAnnotations(p.annotations)

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

    void buildMethodExceptions(MethodDeclaration n, JavaMethod javaMethod) {
        def exceptions = n.thrownExceptions

        if (exceptions.isEmpty()) {
            return
        }

        exceptions.each {
            try {

                def javaField = new JavaField()

                javaField.fieldName = it.asString()

                javaField.fieldType = new JavaReferenceType(simpleName: it.asString())

                javaField.javadocTitle = JavadocHelper.getThrowsComment(n, javaField.fieldName)

                javaMethod.exceptions << javaField
            } catch (Exception e) {
                errors << new JsonError(e)
            }
        }
    }
}
