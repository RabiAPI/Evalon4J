package com.evalon4j.json

import com.evalon4j.frameworks.JacksonAnnotations
import com.evalon4j.frameworks.JaxRSAnnotations
import com.evalon4j.frameworks.OpenAPIAnnotations
import com.evalon4j.frameworks.SpringAnnotations
import com.evalon4j.frameworks.SwaggerAnnotations
import com.evalon4j.frameworks.ValidationAnnotations
import com.evalon4j.frameworks.apidocjs.pojos.ApidocField
import com.evalon4j.java.JavadocComponent
import com.evalon4j.java.types.*
import com.evalon4j.utils.JavaTypeUtils
import groovy.transform.AutoClone

@AutoClone
class JsonStruct extends JavadocComponent {
    String fieldName = ""

    String fieldTypeName = "" // List<String> Map<String, String> etc.

    String fieldSummary = ""

    String fieldDescription = ""

    String fieldFormat = "" // 字段格式，用于日期时间等类型

    String jsonTypeName = "" // Json中的数据类型

    String swaggerDataType = "" // Swagger中的数据类型

    String swaggerDataFormat = "" // Swagger中的数据格式

    String qualifiedName = ""

    String moduleName = ""

    // Properties

    boolean isRequired = false // @NotNull or @NotEmpty

    boolean isRecursive = false

    boolean isDeprecated = false

    boolean isIgnore = false // @JsonIgnore from Jackson

    // Type Flag

    boolean isPrimitiveType = false

    // Primitive Type Flag

    boolean isStringType = false

    boolean isNumberType = false

    boolean isBooleanType = false

    boolean isDatetimeType = false

    boolean isStructType = false

    boolean isArrayType = false

    boolean isSetType = false

    boolean isMapType = false

    boolean isMapKey = false

    boolean isMapValue = false

    boolean isEnumType = false

    boolean isEnumValue = false

    boolean isFileType = false

    String enumValue = ""

    // struct not exists in project, need source jar

    boolean notExists = false

    List<JsonStruct> children = []

    SpringAnnotations springAnnotations = null

    JaxRSAnnotations jaxRSAnnotations = null

    SwaggerAnnotations swaggerAnnotations = null

    OpenAPIAnnotations openAPIAnnotations = null

    ValidationAnnotations validationAnnotations = null

    JacksonAnnotations jacksonAnnotations = null

    JsonConstraint constraint = null

    // Http Parameter

    String parameterType = "" // path query body form header cookie

    String defaultValue = ""

    // Http Response

    JsonStruct() {

    }

    JsonStruct(ApidocField field) {

    }

    JsonStruct(JavaAbstractType abstractType) {
        this.fieldName = abstractType.simpleName

        this.fieldTypeName = abstractType.simpleName

        this.jsonTypeName = JavaTypeUtils.getJsonTypeName(abstractType)

        this.swaggerDataType = JavaTypeUtils.getSwaggerDataType(abstractType)

        this.swaggerDataFormat = JavaTypeUtils.getSwaggerDataFormat(abstractType)

        this.fieldSummary = abstractType.javadocTitle

        this.javadocTitle = abstractType.javadocTitle

        this.javadocContent = abstractType.javadocContent

        this.javadocTags = abstractType.javadocTags

        (abstractType instanceof JavaPrimitiveType) && (this.isPrimitiveType = true)

        (abstractType instanceof JavaArrayType) && (this.isArrayType = true)

        (abstractType instanceof JavaListType) && (this.isArrayType = true)

        (abstractType instanceof JavaSetType) && (this.isArrayType = true)

        (abstractType instanceof JavaMapType) && (this.isMapType = true)

        (abstractType instanceof JavaReferenceType) && (this.isPrimitiveType = true)

        (abstractType instanceof JavaGenericType) && (this.isPrimitiveType = true)

        if (abstractType instanceof JavaPrimitiveType) {
            setPrimitiveTypeFlag(this, abstractType)
        }

        if (abstractType instanceof JavaReferenceType) {
            this.isStructType = true

            this.notExists = abstractType.notExists

            this.qualifiedName = abstractType.qualifiedName

            this.moduleName = abstractType.moduleName
        }

        if (abstractType instanceof JavaGenericType) {
            this.isStructType = true
        }
    }

    private static setPrimitiveTypeFlag(JsonStruct jsonStruct, JavaPrimitiveType primitiveType) {
        JavaTypeUtils.isStringType(primitiveType) && (jsonStruct.isStringType = true)

        JavaTypeUtils.isNumberType(primitiveType) && (jsonStruct.isNumberType = true)

        JavaTypeUtils.isBooleanType(primitiveType) && (jsonStruct.isBooleanType = true)

        JavaTypeUtils.isDatetimeType(primitiveType) && (jsonStruct.isDatetimeType = true)
    }
}
