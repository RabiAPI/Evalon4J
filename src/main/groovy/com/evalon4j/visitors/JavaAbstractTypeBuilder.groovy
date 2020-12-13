package com.evalon4j.visitors

import com.evalon4j.java.types.*
import com.evalon4j.utils.JavaTypeUtils
import com.github.javaparser.ast.NodeList
import com.github.javaparser.ast.type.ArrayType
import com.github.javaparser.ast.type.ClassOrInterfaceType
import com.github.javaparser.ast.type.Type

class JavaAbstractTypeBuilder {
    private String moduleName = ""

    private String packageName = ""

    private List<String> imports = []

    private List<String> typePlaceholders = [] // 泛型占位符

    JavaAbstractTypeBuilder() {

    }

    JavaAbstractTypeBuilder(String moduleName, String packageName, List<String> imports, List<String> typePlaceholders = []) {
        this.moduleName = moduleName

        this.packageName = packageName

        this.imports = imports

        this.typePlaceholders = typePlaceholders
    }

    JavaAbstractType buildFieldTypeRecursive(Type fieldType) {
        if (isPrimitiveType(fieldType)) {
            return new JavaPrimitiveType(fieldType as String)
        }

        if (isArrayType(fieldType)) {
            def javaArrayType = new JavaArrayType()

            fieldType = fieldType.asArrayType()

            javaArrayType.typeArgument = buildFieldTypeRecursive(fieldType.componentType)

            return javaArrayType
        }

        if (isListType(fieldType)) {
            def javaListType = new JavaListType()

            if ((fieldType as ClassOrInterfaceType).typeArguments.present) {
                def type = (fieldType as ClassOrInterfaceType).typeArguments.get()[0] // List 仅有一个泛型占位符

                javaListType.typeArgument = buildFieldTypeRecursive(type)
            } else {
                javaListType.typeArgument = new JavaPrimitiveType("Object")
            }

            return javaListType
        }

        if (isSetType(fieldType)) {
            def javaSetType = new JavaSetType()

            if ((fieldType as ClassOrInterfaceType).typeArguments.present) {
                def type = (fieldType as ClassOrInterfaceType).typeArguments.get()[0] // Set 仅有一个泛型占位符

                javaSetType.typeArgument = buildFieldTypeRecursive(type)
            } else {
                javaSetType.typeArgument = new JavaPrimitiveType("Object")
            }

            return javaSetType
        }

        if (isMapType(fieldType)) {
            def javaMapType = new JavaMapType()

            if ((fieldType as ClassOrInterfaceType).typeArguments.present) {
                def keyType = (fieldType as ClassOrInterfaceType).typeArguments.get()[0]

                def valueType = (fieldType as ClassOrInterfaceType).typeArguments.get()[1]

                javaMapType.keyTypeArgument = buildFieldTypeRecursive(keyType)

                javaMapType.valueTypeArgument = buildFieldTypeRecursive(valueType)
            } else {
                def keyTypeArgument = new JavaPrimitiveType("Object")

                def valueTypeArgument = new JavaPrimitiveType("Object")

                javaMapType.keyTypeArgument = keyTypeArgument

                javaMapType.valueTypeArgument = valueTypeArgument
            }

            return javaMapType
        }

        if (isGenericPlaceholder(typePlaceholders, fieldType)) { // 类型为泛型占位符
            return new JavaTypePlaceholder(simpleName: fieldType.toString())
        }

        if (isGenericReferenceType(fieldType)) {
            fieldType = fieldType as ClassOrInterfaceType

            def javaGenericType = new JavaGenericType(simpleName: fieldType.toString())

            def arguments = fieldType.typeArguments.get()

            arguments.each {
                javaGenericType.typeArguments << new JavaTypeArgument(types: [buildFieldTypeRecursive(it)])
            }

            def args = fieldType.typeArguments

            fieldType.setTypeArguments([] as NodeList<Type>) // 否则无限递归 ???

            javaGenericType.genericType = buildFieldTypeRecursive(fieldType)

            fieldType.setTypeArguments(args.get()) // 还原

            return javaGenericType
        }

        if (isReferenceType(fieldType)) {
            fieldType = fieldType as ClassOrInterfaceType

            def referenceType = new JavaReferenceType(simpleName: fieldType.nameAsString)

            referenceType.moduleName = moduleName

            setQualifiedName(referenceType)

            return referenceType
        }

        return new JavaPrimitiveType(fieldType.toString()) // 不认识的类型直接返回
    }

    private static boolean isPrimitiveType(Type fieldType) {
        return JavaTypeUtils.isPrimitiveType(fieldType as String)
    }

    private static boolean isGenericPlaceholder(List<String> typeArguments, Type fieldType) { // 是否为泛型占位符 S T etc.
        return typeArguments.contains(fieldType as String)
    }

    private static boolean isGenericReferenceType(Type fieldType) { // 带有泛型信息的POJO
        return isReferenceType(fieldType) &&
                (fieldType instanceof ClassOrInterfaceType) &&
                fieldType.typeArguments.present &&
                !fieldType.typeArguments.get().isEmpty()
    }

    private static boolean isArrayType(Type fieldType) {
        return fieldType instanceof ArrayType
    }

    private static boolean isListType(Type fieldType) {
        return (fieldType =~ /List<.*>|List/).matches()
    }

    private static boolean isSetType(Type fieldType) {
        return (fieldType =~ /Set<.*>|Set/).matches()
    }

    private static boolean isMapType(Type fieldType) {
        return (fieldType =~ /Map<.*,.*>|Map/).matches()
    }

    private static boolean isReferenceType(Type fieldType) {
        return !isPrimitiveType(fieldType) &&
                !isArrayType(fieldType) &&
                !isListType(fieldType) &&
                !isMapType(fieldType) &&
                fieldType instanceof ClassOrInterfaceType
    }

    private setQualifiedName(JavaReferenceType referenceType) {
        String qualifiedName = this.imports.find {
            return it.endsWith(referenceType.simpleName)
        }

        if (qualifiedName) {
            referenceType.qualifiedName = qualifiedName

            referenceType.possibleQualifiedNames = [qualifiedName]

            return
        }

        List<String> starImports = imports.findAll {
            return it.endsWith("*")
        }

        if (starImports) {
            referenceType.possibleQualifiedNames = starImports.collect { i ->
                return i.replace("*", referenceType.simpleName)
            }
        }

        // 同一个包

        referenceType.possibleQualifiedNames << (packageName + "." + referenceType.simpleName)
    }
}
