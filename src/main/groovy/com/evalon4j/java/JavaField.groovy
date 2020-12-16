package com.evalon4j.java

import com.evalon4j.frameworks.JacksonAnnotations
import com.evalon4j.frameworks.JaxRSAnnotations
import com.evalon4j.frameworks.OpenAPIAnnotations
import com.evalon4j.frameworks.SpringAnnotations
import com.evalon4j.frameworks.SwaggerAnnotations
import com.evalon4j.frameworks.ValidationAnnotations
import com.evalon4j.java.types.JavaAbstractType
import com.evalon4j.java.types.JavaEnumType
import com.evalon4j.java.types.JavaGenericType
import com.evalon4j.java.types.JavaReferenceType

class JavaField extends JavadocComponent {
    String fieldName

    String fieldTypeName

    String fieldQualifiedName

    JavaAbstractType fieldType

    String fieldValue // Default Value

    boolean isRequired = false

    boolean isRecursive = false

    boolean isDeprecated = false

    boolean isIgnore = false

    SpringAnnotations springAnnotations = null

    JaxRSAnnotations jaxRSAnnotations = null

    SwaggerAnnotations swaggerAnnotations = null

    OpenAPIAnnotations openAPIAnnotations = null

    ValidationAnnotations validationAnnotations = null

    JacksonAnnotations jacksonAnnotations = null

    String getFieldQualifiedName() {
        if (fieldType instanceof JavaGenericType && fieldType.genericType instanceof JavaReferenceType) {
            return (fieldType.genericType as JavaReferenceType).packageName + fieldType.simpleName // 对于泛型，返回整个泛型定义，否则递归判断会出错
        }

        if (fieldType instanceof JavaEnumType) {
            return fieldType.qualifiedName
        }

        if (fieldType instanceof JavaReferenceType) {
            return fieldType.qualifiedName
        }

        return null
    }
}
