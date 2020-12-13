package com.evalon4j.utils

import com.evalon4j.java.types.*

class JavaTypeUtils {
    private static JAVA_STRING_TYPES = [
            "char",
            "Character",
            "String",
    ]

    private static JAVA_NUMBER_TYPES = [
            "byte",
            "Byte",
            "short",
            "Short",
            "int",
            "Integer",
            "long",
            "Long",
            "float",
            "Float",
            "double",
            "Double",
            "BigInteger",
            "BigDecimal"
    ]

    private static JAVA_INTEGER_TYPES = [
            "byte",
            "Byte",
            "short",
            "Short",
            "int",
            "Integer"
    ]

    private static JAVA_LONG_TYPES = [
            "long",
            "Long",
            "BigInteger",
    ]

    private static JAVA_DECIMAL_TYPES = [
            "float",
            "Float",
            "double",
            "Double",
            "BigDecimal",
    ]

    private static JAVA_DATETIME_TYPES = [
            "Date",
            "Timestamp",
            "LocalDate",
            "LocalTime",
            "LocalDateTime",
    ]

    private static JAVA_BOOLEAN_TYPES = [
            "boolean",
            "Boolean",
    ]

    private static JAVA_FILE_TYPES = [
            "File",
            "MultipartFile"
    ]

    static isPrimitiveType(JavaAbstractType javaAbstractType) {
        return isStringType(javaAbstractType) ||
                isNumberType(javaAbstractType) ||
                isDatetimeType(javaAbstractType) ||
                isFileType(javaAbstractType) ||
                isBooleanType(javaAbstractType)
    }

    static isPrimitiveType(String typeName) {
        return isStringType(typeName) ||
                isNumberType(typeName) ||
                isDatetimeType(typeName) ||
                isFileType(typeName) ||
                isBooleanType(typeName)
    }

    static isStringType(JavaAbstractType javaAbstractType) {
        return isStringType(javaAbstractType.simpleName)
    }

    static isStringType(String typeName) {
        return JAVA_STRING_TYPES.contains(typeName)
    }

    static isNumberType(JavaAbstractType javaAbstractType) {
        return isNumberType(javaAbstractType.simpleName)
    }

    static isNumberType(String typeName) {
        return JAVA_NUMBER_TYPES.contains(typeName)
    }

    static isInteger32Type(JavaAbstractType javaAbstractType) {
        return isInteger32Type(javaAbstractType.simpleName)
    }

    static isInteger32Type(String typeName) {
        return JAVA_INTEGER_TYPES.contains(typeName)
    }

    static isInteger64Type(JavaAbstractType javaAbstractType) {
        return isInteger64Type(javaAbstractType.simpleName)
    }

    static isInteger64Type(String typeName) {
        return JAVA_LONG_TYPES.contains(typeName)
    }

    static isDecimalType(JavaPrimitiveType primitiveType) {
        return isDecimalType(primitiveType.simpleName)
    }

    static isDecimalType(String typeName) {
        return JAVA_DECIMAL_TYPES.contains(typeName)
    }

    static isBooleanType(JavaAbstractType javaAbstractType) {
        return isBooleanType(javaAbstractType.simpleName)
    }

    static isBooleanType(String typeName) {
        return JAVA_BOOLEAN_TYPES.contains(typeName)
    }

    static isDatetimeType(JavaAbstractType javaAbstractType) {
        return isDatetimeType(javaAbstractType.simpleName)
    }

    static isDatetimeType(String typeName) {
        return JAVA_DATETIME_TYPES.contains(typeName)
    }

    static isFileType(JavaAbstractType javaAbstractType) {
        return isFileType(javaAbstractType.simpleName)
    }

    static isFileType(String typeName) {
        return JAVA_FILE_TYPES.contains(typeName)
    }

    static isBinaryType(JavaPrimitiveType primitiveType) {
        // ignore
    }

    static getJsonTypeName(JavaAbstractType abstractType) {
        if (abstractType instanceof JavaReferenceType) {
            if (abstractType.isEnum) {
                return JsonDataTypes.ENUM
            } else {
                return JsonDataTypes.OBJECT
            }
        }

        if (abstractType instanceof JavaGenericType) {
            return JsonDataTypes.OBJECT
        }

        if (abstractType instanceof JavaMapType) {
            return JsonDataTypes.DICT
        }

        if (abstractType instanceof JavaArrayType || abstractType instanceof JavaListType || abstractType instanceof JavaSetType) {
            return JsonDataTypes.ARRAY
        }

        if (abstractType instanceof JavaPrimitiveType) {
            if (isStringType(abstractType)) { // String类型和Java相同
                return JsonDataTypes.EMPTY
            }

            if (isNumberType(abstractType)) {
                return JsonDataTypes.NUMBER
            }

            if (isDatetimeType(abstractType)) {
                return JsonDataTypes.DATETIME
            }

            if (isBooleanType(abstractType)) {
                return JsonDataTypes.BOOLEAN
            }
        }

        return JsonDataTypes.STRING
    }

    static getSwaggerDataType(String typeName) {
        if (isStringType(typeName)) { // String类型和Java相同
            return SwaggerDataTypes.STRING
        }

        if (isInteger32Type(typeName)) {
            return SwaggerDataTypes.INTEGER
        }

        if (isInteger64Type(typeName)) {
            return SwaggerDataTypes.INTEGER
        }

        if (isDecimalType(typeName)) {
            return SwaggerDataTypes.NUMBER
        }

        if (isDatetimeType(typeName)) {
            return SwaggerDataTypes.STRING
        }

        if (isBooleanType(typeName)) {
            return SwaggerDataTypes.BOOLEAN
        }

        if (isFileType(typeName)) {
            return SwaggerDataTypes.FILE
        }

        return SwaggerDataTypes.STRING
    }

    static getSwaggerDataType(JavaAbstractType abstractType) {
        if (abstractType instanceof JavaReferenceType) {
            if (abstractType.isEnum) {
                return SwaggerDataTypes.STRING
            } else {
                return SwaggerDataTypes.OBJECT
            }
        }

        if (abstractType instanceof JavaGenericType) {
            return SwaggerDataTypes.OBJECT
        }

        if (abstractType instanceof JavaMapType) {
            return SwaggerDataTypes.STRING // string + additionalProperties
        }

        if (abstractType instanceof JavaArrayType
                || abstractType instanceof JavaListType
                || abstractType instanceof JavaSetType) {
            return SwaggerDataTypes.ARRAY
        }

        if (abstractType instanceof JavaPrimitiveType) {
            if (isStringType(abstractType)) { // String类型和Java相同
                return SwaggerDataTypes.STRING
            }

            if (isInteger32Type(abstractType)) {
                return SwaggerDataTypes.INTEGER
            }

            if (isDecimalType(abstractType)) {
                return SwaggerDataTypes.NUMBER
            }

            if (isDatetimeType(abstractType)) {
                return SwaggerDataTypes.STRING
            }

            if (isBooleanType(abstractType)) {
                return SwaggerDataTypes.BOOLEAN
            }

            if (isFileType(abstractType)) {
                return SwaggerDataTypes.FILE
            }
        }

        return SwaggerDataTypes.STRING
    }

    static getSwaggerDataFormat(String typeName) {
        if (isStringType(typeName)) {
            return SwaggerDataFormats.EMPTY
        }

        if (isInteger32Type(typeName)) {
            return SwaggerDataFormats.INT32
        }

        if (isInteger64Type(typeName)) {
            return SwaggerDataFormats.INT64
        }

        if (isDecimalType(typeName)) {
            return SwaggerDataFormats.DOUBLE
        }

        if (isDatetimeType(typeName)) {
            return SwaggerDataFormats.DATETIME
        }

        return SwaggerDataFormats.EMPTY
    }

    static getSwaggerDataFormat(JavaAbstractType abstractType) {
        if (abstractType instanceof JavaPrimitiveType) {
            if (isStringType(abstractType)) {
                return SwaggerDataFormats.EMPTY
            }

            if (isInteger32Type(abstractType)) {
                return SwaggerDataFormats.INT32
            }

            if (isInteger64Type(abstractType)) {
                return SwaggerDataFormats.INT64
            }

            if (isDecimalType(abstractType)) {
                return SwaggerDataFormats.DOUBLE
            }

            if (isDatetimeType(abstractType)) {
                return SwaggerDataFormats.DATETIME
            }
        }

        return SwaggerDataFormats.EMPTY
    }
}
