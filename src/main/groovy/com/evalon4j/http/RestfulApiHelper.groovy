package com.evalon4j.http

import com.evalon4j.frameworks.openapi.Parameter
import com.evalon4j.frameworks.openapi.media.ArraySchema
import com.evalon4j.frameworks.openapi.media.Content
import com.evalon4j.frameworks.openapi.media.Schema
import com.evalon4j.json.JsonStruct
import com.evalon4j.transformer.JsonStructTransformer

class RestfulApiHelper {
    static findParameter(String parameterName, List<JsonStruct> parameters) {
        return parameters.find {
            it.fieldName == parameterName
        }
    }

    static JsonStruct transformSchemaToJsonStruct(Schema schema) {
        if (!schema) {
            return null
        }

        def jsonStruct = null

        if (schema.implementation) {
            jsonStruct = JsonStructTransformer.transformJavaAbstractTypeToJsonStruct(schema.implementation)
        }

        // 其它 oneOf allOf 都不支持

        return jsonStruct
    }

    static JsonStruct transformArraySchemaToJsonStruct(ArraySchema arraySchema) {
        if (!arraySchema) {
            return null
        }

        def jsonStruct = null

        if (arraySchema.schema) {
            arraySchema.schema.implementation && (jsonStruct = JsonStructTransformer.transformJavaAbstractTypeToJsonStruct(arraySchema.schema.implementation))
        }

        // 其它 oneOf allOf 都不支持

        return jsonStruct
    }

    static JsonStruct transformContentToJsonStruct(Content content) {
        if (!content) {
            return null
        }

        def jsonStruct = null

        if (content.schema) {
            content.schema.implementation && (jsonStruct = JsonStructTransformer.transformJavaAbstractTypeToJsonStruct(content.schema.implementation))
        }

        if (content.array && content.array.schema) {
            content.array.schema.implementation && (jsonStruct = JsonStructTransformer.transformJavaAbstractTypeToJsonStruct(content.array.schema.implementation))
        }

        return jsonStruct
    }

    static JsonStruct transformOpenAPIParameterToJsonStruct(Parameter p) {
        def jsonStruct = null

        if (p.schema) { // 仅支持 implementation
            jsonStruct = transformSchemaToJsonStruct(p.schema)
        }

        if (p.array) {
            jsonStruct = transformArraySchemaToJsonStruct(p.array)
        }

        if (p.content) {
            def content = p.content.first()

            jsonStruct = transformContentToJsonStruct(content)
        }

        if (!jsonStruct) {
            return null
        }

        jsonStruct.fieldName = p.name

        jsonStruct.parameterType = p.in_.toString().toLowerCase()

        p.description && (jsonStruct.fieldSummary = p.description)

        jsonStruct.isRequired = p.required

        jsonStruct.isDeprecated = p.deprecated

        return jsonStruct
    }
}
