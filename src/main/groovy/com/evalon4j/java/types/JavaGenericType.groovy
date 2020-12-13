package com.evalon4j.java.types


import com.evalon4j.java.JavaField
import com.evalon4j.visitors.JavaVisitorHelper

class JavaGenericType extends JavaAbstractType {
    JavaAbstractType genericType

    List<JavaTypeArgument> typeArguments = []

    JavaReferenceType build() {
        // 得到泛型参数 S T etc.
        List<String> typePlaceholders = (genericType as JavaReferenceType).typePlaceholders

        // 得到泛型容器，先把自己克隆了嗷
        JavaReferenceType containerType = JavaVisitorHelper.deepCopyJavaAbstractType(this.genericType) as JavaReferenceType

        containerType.simpleName = this.simpleName

        containerType.qualifiedName = containerType.packageName + "." + containerType.simpleName

        if (!typePlaceholders) { // 第三方依赖的泛型容器，无法解析占位符，手动进行填充
            typeArguments.each { argument ->
                def types = argument.types

                types && (typePlaceholders << types[0].simpleName)
            }
        }

        // 存放泛型参数和对应的实际类型
        Map<String, JavaAbstractType> placeholderToActualType = [:]

        // 替换 进行实际的替换操作
        Closure replaceGenericPlaceholder = { JavaAbstractType javaAbstractType, Map<String, JavaAbstractType> placeholderToActualTypes ->
            if (!(javaAbstractType instanceof JavaTypePlaceholder)) {
                return javaAbstractType
            }

            def actualType = placeholderToActualTypes[javaAbstractType.simpleName]

            if (actualType instanceof JavaGenericType) {
                return actualType.build()
            }

            return actualType
        }

        Closure recurseExtensions // 不分开写的话无法递归

        recurseExtensions = { List<JavaAbstractType> extensions ->
            extensions.each { ext ->
                if (ext instanceof JavaGenericType) {
                    ext = ext.build()
                }

                if (ext instanceof JavaReferenceType) {
                    ext.fields.each { field ->
                        if (checkFieldExists(field)) {
                            field.fieldType = JavaVisitorHelper.replaceJavaAbstractTypeWithAction(field.fieldType,
                                    placeholderToActualType, replaceGenericPlaceholder) as JavaAbstractType

                            field.fieldTypeName = field.fieldType.getSimpleName()
                        }
                    }

                    recurseExtensions(ext.extensions)
                }
            }
        }

        // 查询泛型参数对应的实际类型
        (0..typePlaceholders.size()).each { index ->
            typePlaceholders[index] && (placeholderToActualType[typePlaceholders[index]] = typeArguments[index].types.first())
        }

        containerType.fields.each { field ->
            field.fieldType = JavaVisitorHelper.replaceJavaAbstractTypeWithAction(field.fieldType, placeholderToActualType, replaceGenericPlaceholder) as JavaAbstractType

            field.fieldTypeName = field.fieldType.getSimpleName()
        }

        if (!containerType.fields) { // 没有解析到的，作填充操作
            typeArguments.each { argument ->
                if (!argument.types) {
                    return
                }

                def type = argument.types[0]

                def placeholderJavaField = new JavaField()

                placeholderJavaField.fieldName = type.simpleName

                placeholderJavaField.fieldType = type

                containerType.fields << placeholderJavaField
            }
        }

//        containerType.methods.each { method ->
//            method.parameters.each {parameter ->
//                parameter.fieldType = JavaVisitorHelper.replaceJavaAbstractTypeWithAction(parameter.fieldType, placeholderToActualType, replaceGenericPlaceholder) as JavaAbstractType
//
//                parameter.fieldTypeName = parameter.fieldType.getSimpleName()
//            }
//
//            method.response && (method.response = JavaVisitorHelper.replaceJavaAbstractTypeWithAction(method.response, placeholderToActualType, replaceGenericPlaceholder) as JavaAbstractType)
//        }

        recurseExtensions(containerType.extensions)

        return containerType
    }

    static boolean checkFieldExists(JavaField field) {
        if (!field) {
            return false
        }

        if (!field.fieldType) {
            return false
        }

        return true
    }
}
