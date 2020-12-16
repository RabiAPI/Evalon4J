package com.evalon4j.visitors

import com.evalon4j.java.JavaField
import com.evalon4j.java.JavaMethod
import com.evalon4j.java.types.*
import com.evalon4j.json.JsonStruct
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration
import com.github.javaparser.ast.body.MethodDeclaration
import com.github.javaparser.ast.body.Parameter
import com.github.javaparser.ast.expr.AnnotationExpr
import com.github.javaparser.ast.expr.MemberValuePair
import com.github.javaparser.ast.expr.StringLiteralExpr

class JavaVisitorHelper {
    private static final String ANNOTATION_DEPRECATED = "Deprecated" // @deprecated annotation

    private static final String ANNOTATION_NOT_NULL = "NotNull"

    private static final String ANNOTATION_NOT_EMPTY = "NotEmpty"

    private static final String ANNOTATION_NOT_BLANK = "NotBlank"

    /**
     * 类是否已废弃
     */
    static boolean isDeprecated(ClassOrInterfaceDeclaration n) {
        def annotations = n.annotations

        if (annotations.any { it.nameAsString == ANNOTATION_DEPRECATED }) {
            return true
        }

        return false
    }

    /**
     * 方法是否已废弃
     */
    static boolean isDeprecated(MethodDeclaration n) {
        def annotations = n.annotations

        if (annotations.any { it.nameAsString == ANNOTATION_DEPRECATED }) {
            return true
        }

        return false
    }

    /**
     * 参数是否必须
     */
    static boolean isRequired(Parameter n) {
        def annotations = n.annotations

        if (annotations.any {
            it.nameAsString == ANNOTATION_NOT_NULL || it.nameAsString == ANNOTATION_NOT_EMPTY || it.nameAsString == ANNOTATION_NOT_BLANK
        }) {
            return true
        }

        return false
    }

    @SuppressWarnings("GroovyAssignabilityCheck")
    static replaceJavaAbstractTypeWithAction(JavaAbstractType javaAbstractType, Map<String, JavaAbstractType> placeholderToActualTypes, Closure action) {
        if (javaAbstractType instanceof JavaPrimitiveType) {
            return javaAbstractType
        }

        if (javaAbstractType instanceof JavaListType) {
            javaAbstractType.typeArgument = replaceJavaAbstractTypeWithAction(javaAbstractType.typeArgument, placeholderToActualTypes, action)

            return javaAbstractType
        }

        if (javaAbstractType instanceof JavaSetType) {
            javaAbstractType.typeArgument = replaceJavaAbstractTypeWithAction(javaAbstractType.typeArgument, placeholderToActualTypes, action)

            return javaAbstractType
        }

        if (javaAbstractType instanceof JavaMapType) {
            javaAbstractType.keyTypeArgument = replaceJavaAbstractTypeWithAction(javaAbstractType.keyTypeArgument, placeholderToActualTypes, action)

            javaAbstractType.valueTypeArgument = replaceJavaAbstractTypeWithAction(javaAbstractType.valueTypeArgument, placeholderToActualTypes, action)

            return javaAbstractType
        }

        if (javaAbstractType instanceof JavaTypePlaceholder) {
            return action(javaAbstractType, placeholderToActualTypes)
        }

        if (javaAbstractType instanceof JavaGenericType) {
            return action(javaAbstractType, placeholderToActualTypes)
        }

        if (javaAbstractType instanceof JavaReferenceType) {
            return action(javaAbstractType, placeholderToActualTypes)
        }
    }

    static iterateJavaPojoType(JavaAbstractType javaAbstractType, Closure action, Set<String> set = []) {
        if (!javaAbstractType) {
            return
        }

        if (javaAbstractType instanceof JavaPrimitiveType) {
            return
        }

        if (javaAbstractType instanceof JavaArrayType) {
            iterateJavaPojoType(javaAbstractType.typeArgument, action, set)

            return
        }

        if (javaAbstractType instanceof JavaListType) {
            iterateJavaPojoType(javaAbstractType.typeArgument, action, set)

            return
        }

        if (javaAbstractType instanceof JavaSetType) {
            iterateJavaPojoType(javaAbstractType.typeArgument, action, set)

            return
        }

        if (javaAbstractType instanceof JavaMapType) {
            iterateJavaPojoType(javaAbstractType.keyTypeArgument, action, set)

            iterateJavaPojoType(javaAbstractType.valueTypeArgument, action, set)

            return
        }

        if (javaAbstractType instanceof JavaGenericType) {
            if (javaAbstractType.genericType) {
                action(javaAbstractType.genericType)
            }

            javaAbstractType.typeArguments.each {
                iterateJavaPojoType(it.types.first(), action, set)
            }

            return
        }

        if (javaAbstractType instanceof JavaReferenceType) {
            if (!(set.add(javaAbstractType.simpleName))) { // 排除已经遍历过的类
                return
            }

            action(javaAbstractType)

//            javaAbstractType.extensions.each {
//                iterateJavaPojoType(it, action, set)
//            }
//
//            javaAbstractType.innerPojos.each {
//                iterateJavaPojoType(it, action, set)
//            }

            //递归执行
            (javaAbstractType as JavaReferenceType).fields.each { field ->
                if (field.fieldType) {
                    iterateJavaPojoType(field.fieldType, action, set)
                }
            }

            return
        }
    }

    static void setQualifiedNameForJavaAbstractType(List<String> imports, String packageName, JavaAbstractType javaAbstractType) {
        // Primitive Type

        if (javaAbstractType instanceof JavaPrimitiveType) {
            return
        }

        // Array Type

        if (javaAbstractType instanceof JavaArrayType) {
            setQualifiedNameForJavaAbstractType(imports, packageName, javaAbstractType.typeArgument)
            return
        }

        if (javaAbstractType instanceof JavaListType) {
            setQualifiedNameForJavaAbstractType(imports, packageName, javaAbstractType.typeArgument)
            return
        }

        if (javaAbstractType instanceof JavaSetType) {
            setQualifiedNameForJavaAbstractType(imports, packageName, javaAbstractType.typeArgument)
            return
        }

        // Map Type

        if (javaAbstractType instanceof JavaMapType) {
            setQualifiedNameForJavaAbstractType(imports, packageName, javaAbstractType.keyTypeArgument)

            setQualifiedNameForJavaAbstractType(imports, packageName, javaAbstractType.valueTypeArgument)

            return
        }

        // Reference Type

        if (javaAbstractType instanceof JavaReferenceType || javaAbstractType instanceof JavaEnumType) {
            String qualifiedName = imports.find {
                return it.endsWith(javaAbstractType.simpleName)
            }

            if (qualifiedName) {
                javaAbstractType.qualifiedName = qualifiedName

                javaAbstractType.possibleQualifiedNames = [qualifiedName]

                return
            }

            List<String> starImports = imports.findAll {
                return it.endsWith("*")
            }

            if (starImports) {
                javaAbstractType.possibleQualifiedNames = starImports.collect { i ->
                    return i.replace("*", javaAbstractType.simpleName)
                }
            }

            // 同一个包

            javaAbstractType.possibleQualifiedNames << packageName + "." + javaAbstractType.simpleName

            return
        }

        if (javaAbstractType instanceof JavaGenericType) {
            setQualifiedNameForJavaAbstractType(imports, packageName, javaAbstractType.genericType)

            javaAbstractType.typeArguments.each { typeArgument ->
                if (!typeArgument.types) {
                    return
                }

                setQualifiedNameForJavaAbstractType(imports, packageName, typeArgument.types.first())
            }
        }
    }

    static String getPathFromMapping(AnnotationExpr mapping) {
        if (!mapping) {
            return ""
        }

        def pair = mapping.childNodes.find {
            it instanceof MemberValuePair && (it.nameAsString == "path" || it.nameAsString == "value" || it.nameAsString == "name")
        } as MemberValuePair

        if (!pair) {
            return ""
        }

        def path

        if (pair.value instanceof StringLiteralExpr) {
            path = pair.value.asLiteralStringValueExpr().value
        } else {
            path = pair.value.toString()
        }

        if (path.endsWith("/")) { // 去掉结尾的 / 符号
            path = path.substring(0, path.length() - 1)
        }

        if (!path.startsWith("/")) {
            path = "/" + path
        }

        return path
    }

    static List<JsonStruct> deepCopyJsonStructs(List<JsonStruct> jsonStructs) {
        if (!jsonStructs) {
            return []
        }

        def clonedJsonStructs = jsonStructs.collect {
            if (!it) { // !!! 存在空指针问题，原因不明
                return null
            }

            def clonedJsonStruct = it.clone()

            clonedJsonStruct.children = deepCopyJsonStructs(clonedJsonStruct.children) as List<JsonStruct>

            return clonedJsonStruct
        }

        return clonedJsonStructs.findAll { it !== null } as List<JsonStruct>
    }

    /**
     * Deep copy a JavaAbstractType for generic analysis
     */
    static JavaAbstractType deepCopyJavaAbstractType(JavaAbstractType source, DependencyTree dependencyTree = null) {
        if (source instanceof JavaPrimitiveType) {
            return new JavaPrimitiveType(simpleName: source.simpleName)
        }

        if (source instanceof JavaGenericType) {
            !dependencyTree && (dependencyTree = new DependencyTree(qualifiedName: source.genericType.qualifiedName))

            def target = new JavaGenericType()

            target.simpleName = source.simpleName

            target.genericType = deepCopyJavaAbstractType(source.genericType, dependencyTree)

            source.typeArguments.each {
                it.types && target.typeArguments << new JavaTypeArgument(types: [deepCopyJavaAbstractType(it.types[0], dependencyTree)])
            }

            return target
        }

        if (source instanceof JavaEnumType) {
            return source // ignore enum
        }

        if (source instanceof JavaReferenceType) {
            !dependencyTree && (dependencyTree = new DependencyTree(qualifiedName: source.qualifiedName))

            def flag = dependencyTree.isRecursive(source.qualifiedName)

            if (flag) {
                return source
            } else {
                dependencyTree = new DependencyTree(qualifiedName: source.qualifiedName, parent: dependencyTree)
            }

            def target = new JavaReferenceType()

            target.possibleQualifiedNames = new ArrayList<>(source.possibleQualifiedNames)

            target.qualifiedName = source.qualifiedName

            target.packageName = source.packageName

            target.moduleName = source.moduleName

            target.imports = new ArrayList<>(source.imports)

            target.typePlaceholders = new ArrayList<>(source.typePlaceholders)

            source.extensions.each { ext ->
                target.extensions << (deepCopyJavaAbstractType(ext, dependencyTree))
            }

            source.fields.each { sourceField ->
                target.fields << deepCopyJavaField(sourceField, dependencyTree)
            }

            source.methods.each {sourceMethod ->
                target.methods << deepCopyJavaMethod(sourceMethod, dependencyTree)
            }

            target.isRecursive = source.isRecursive

            target.isDeprecated = source.isDeprecated

            target.notExists = source.notExists

            return target
        }

        if (source instanceof JavaArrayType) {
            source.typeArgument = deepCopyJavaAbstractType(source.typeArgument, dependencyTree)

            return source
        }

        if (source instanceof JavaListType) {
            source.typeArgument = deepCopyJavaAbstractType(source.typeArgument, dependencyTree)

            return source
        }

        if (source instanceof JavaSetType) {
            source.typeArgument = deepCopyJavaAbstractType(source.typeArgument, dependencyTree)

            return source
        }

        if (source instanceof JavaMapType) {
            source.keyTypeArgument = deepCopyJavaAbstractType(source.keyTypeArgument, dependencyTree)

            source.valueTypeArgument = deepCopyJavaAbstractType(source.valueTypeArgument, dependencyTree)

            return source
        }

        if (source instanceof JavaTypePlaceholder) {
            return new JavaTypePlaceholder(simpleName: source.simpleName)
        }
    }

    static deepCopyJavaField(JavaField sourceField, DependencyTree dependencyTree = null) {
        def targetField = new JavaField()

        targetField.fieldName = sourceField.fieldName

        targetField.fieldTypeName = sourceField.fieldTypeName

        targetField.fieldType = deepCopyJavaAbstractType(sourceField.fieldType, dependencyTree)

        targetField.fieldQualifiedName = sourceField.fieldQualifiedName

        targetField.isDeprecated = sourceField.isDeprecated

        targetField.isRecursive = sourceField.isRecursive

        targetField.isRequired = sourceField.isRequired

        targetField.isIgnore = sourceField.isIgnore

        targetField.springAnnotations = sourceField.springAnnotations

        targetField.jaxRSAnnotations = sourceField.jaxRSAnnotations

        targetField.swaggerAnnotations = sourceField.swaggerAnnotations

        targetField.openAPIAnnotations = sourceField.openAPIAnnotations

        targetField.jacksonAnnotations = sourceField.jacksonAnnotations

        return targetField
    }

    static deepCopyJavaMethod(JavaMethod sourceMethod, DependencyTree dependencyTree = null) {
        def targetMethod = new JavaMethod()

        targetMethod.serviceName = sourceMethod.serviceName

        targetMethod.serviceQualifiedName = sourceMethod.serviceQualifiedName

        targetMethod.serviceJavadocTitle = sourceMethod.serviceJavadocTitle

        targetMethod.serviceJavadocContent = sourceMethod.serviceJavadocContent

        targetMethod.methodName = sourceMethod.methodName

        targetMethod.isDeprecated = sourceMethod.isDeprecated

        sourceMethod.parameters.each { sourceParameter ->
            targetMethod.parameters << deepCopyJavaField(sourceParameter, dependencyTree)
        }

        if (sourceMethod.response) {
            targetMethod.response = deepCopyJavaAbstractType(sourceMethod.response, dependencyTree)
        }

        targetMethod.springAnnotations = sourceMethod.springAnnotations

        targetMethod.jaxRSAnnotations = sourceMethod.jaxRSAnnotations

        targetMethod.swaggerAnnotations = sourceMethod.swaggerAnnotations

        targetMethod.openAPIAnnotations = sourceMethod.openAPIAnnotations

        return targetMethod
    }
}
