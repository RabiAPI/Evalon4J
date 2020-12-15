package com.evalon4j.visitors

import com.evalon4j.frameworks.OpenAPIAnnotations
import com.evalon4j.frameworks.SwaggerAnnotations
import com.evalon4j.frameworks.openapi.Parameter
import com.evalon4j.frameworks.openapi.media.ArraySchema
import com.evalon4j.frameworks.openapi.media.Content
import com.evalon4j.frameworks.openapi.media.Schema
import com.evalon4j.frameworks.openapi.responses.ApiResponse
import com.evalon4j.frameworks.swagger.ApiImplicitParam
import com.evalon4j.java.JavaMethod
import com.evalon4j.java.JavaProject
import com.evalon4j.java.types.*

class JavaReferenceBuilder {
    def buildReference(JavaProject javaProject) {
        buildClassReference(javaProject)

        buildServiceReference(javaProject)
    }

    private buildServiceReference(JavaProject javaProject) {
        def interfaces = javaProject.interfaces

        def classes = javaProject.classes

        def referenceTypes = javaProject.references

        interfaces.each { javaInterface ->
            buildSwaggerAnnotationsReference(javaInterface.swaggerAnnotations, referenceTypes)

            buildOpenAPIAnnotationsReference(javaInterface.openAPIAnnotations, referenceTypes)

            javaInterface.extensions && (javaInterface.extensions = [resolveFieldTypeRecursive(javaInterface.extensions[0], referenceTypes)])

            javaInterface.methods.each { javaMethod ->
                buildMethodReference(javaMethod, referenceTypes)
            }
        }

        classes.each { javaClass ->
            buildSwaggerAnnotationsReference(javaClass.swaggerAnnotations, referenceTypes)

            buildOpenAPIAnnotationsReference(javaClass.openAPIAnnotations, referenceTypes)

            if (javaClass.extensions) {
                javaClass.extensions = [resolveFieldTypeRecursive(javaClass.extensions[0], referenceTypes)]
            }

            javaClass.methods.each { javaMethod ->
                buildMethodReference(javaMethod, referenceTypes)
            }
        }
    }

    private buildMethodReference(JavaMethod javaMethod, List<JavaReferenceType> referenceTypes) {
        buildSwaggerAnnotationsReference(javaMethod.swaggerAnnotations, referenceTypes)

        buildOpenAPIAnnotationsReference(javaMethod.openAPIAnnotations, referenceTypes)

        javaMethod.parameters.each { parameter ->
            buildSwaggerAnnotationsReference(parameter.swaggerAnnotations, referenceTypes)

            buildOpenAPIAnnotationsReference(parameter.openAPIAnnotations, referenceTypes)

            parameter.fieldType = resolveFieldTypeRecursive(parameter.fieldType, referenceTypes)
        }

        if (javaMethod.response) {
            javaMethod.response = resolveFieldTypeRecursive(javaMethod.response, referenceTypes)
        }
    }

    private buildClassReference(JavaProject javaProject) {
        buildExtensionReference(javaProject.references)

        buildFieldReference(javaProject.references)
    }

    private buildExtensionReference(List<JavaReferenceType> referenceTypes) {
        referenceTypes.each { referenceType ->
            if (!referenceType.extensions) {
                return
            }

            def extension = referenceType.extensions.first()

            referenceType.extensions = [resolveFieldTypeRecursive(extension, referenceTypes)] as List<JavaReferenceType>
        }
    }

    private buildFieldReference(List<JavaReferenceType> referenceTypes) {
        referenceTypes.each { referenceType ->
            referenceType.fields.each { field ->
                field.fieldType = resolveFieldTypeRecursive(field.fieldType, referenceTypes)
            }
        }
    }

    private buildSwaggerAnnotationsReference(SwaggerAnnotations swaggerAnnotations, List<JavaReferenceType> referenceTypes) {
        if (!swaggerAnnotations) {
            return
        }

        def apiOperation = swaggerAnnotations.apiOperation

        List<ApiImplicitParam> parameters = []

        List<com.evalon4j.frameworks.swagger.ApiResponse> responses = []

        swaggerAnnotations.apiImplicitParams && (parameters.addAll(swaggerAnnotations.apiImplicitParams.value))

        swaggerAnnotations.apiImplicitParam && (parameters.addAll(swaggerAnnotations.apiImplicitParam))

        swaggerAnnotations.apiResponses && (responses.addAll(swaggerAnnotations.apiResponses.value))

        swaggerAnnotations.apiResponse && (responses.addAll(swaggerAnnotations.apiResponse))

        if (apiOperation && apiOperation.response) {
            apiOperation.response = resolveFieldTypeRecursive(apiOperation.response, referenceTypes)

            if (apiOperation.responseContainer == "List") {
                apiOperation.response = new JavaListType(apiOperation.response)
            }

            if (apiOperation.responseContainer == "Set") {
                apiOperation.response = new JavaSetType(apiOperation.response)
            }

            if (apiOperation.responseContainer == "Map") {
                apiOperation.response = new JavaMapType(new JavaPrimitiveType(), apiOperation.response)
            }
        }

        parameters.each { apiImplicitParam ->
            if (!apiImplicitParam.dataTypeClass) {
                return
            }

            apiImplicitParam.dataTypeClass = resolveFieldTypeRecursive(apiImplicitParam.dataTypeClass, referenceTypes)
        }

        responses.each { apiResponse ->
            if (!apiResponse.response) {
                return
            }

            apiResponse.response = resolveFieldTypeRecursive(apiResponse.response, referenceTypes)

            if (apiResponse.responseContainer == "List") {
                apiResponse.response = new JavaListType(apiResponse.response)
            }

            if (apiResponse.responseContainer == "Set") {
                apiResponse.response = new JavaSetType(apiResponse.response)
            }

            if (apiResponse.responseContainer == "Map") {
                apiResponse.response = new JavaMapType(new JavaPrimitiveType(), apiResponse.response)
            }
        }
    }

    private buildOpenAPIAnnotationsReference(OpenAPIAnnotations openAPIAnnotations, List<JavaReferenceType> referenceTypes) {
        if (!openAPIAnnotations) {
            return
        }

        def operation = openAPIAnnotations.operation

        List<Parameter> parameters = []

        List<ApiResponse> responses = []

        operation && (parameters.addAll(operation.parameters))

        openAPIAnnotations.parameters && (parameters.addAll(openAPIAnnotations.parameters.value))

        openAPIAnnotations.parameter && (parameters.addAll(openAPIAnnotations.parameter))

        operation && (responses.addAll(operation.responses))

        openAPIAnnotations.parameters && (responses.addAll(openAPIAnnotations.apiResponses.value))

        openAPIAnnotations.parameter && (responses.addAll(openAPIAnnotations.apiResponse))

        parameters.each { parameter ->
            buildOpenAPISchemaReference(parameter.schema, referenceTypes)

            buildOpenAPIArraySchemaReference(parameter.array, referenceTypes)

            parameter.content.each {
                buildOpenAPIContentReference(it, referenceTypes)
            }
        }

        if (operation && operation.requestBody) {
            operation.requestBody.content.each { content ->
                buildOpenAPIContentReference(content, referenceTypes)
            }
        }

        responses.each { response ->
            response.content.each { content ->
                buildOpenAPIContentReference(content, referenceTypes)
            }
        }
    }

    private buildOpenAPISchemaReference(Schema schema, List<JavaReferenceType> referenceTypes) {
        if (!schema) {
            return
        }

        schema.implementation && (schema.implementation = resolveFieldTypeRecursive(schema.implementation, referenceTypes))
    }

    private buildOpenAPIArraySchemaReference(ArraySchema arraySchema, List<JavaReferenceType> referenceTypes) {
        if (!arraySchema) {
            return
        }

        buildOpenAPISchemaReference(arraySchema.schema, referenceTypes)

        buildOpenAPISchemaReference(arraySchema.arraySchema, referenceTypes)
    }

    private buildOpenAPIContentReference(Content content, List<JavaReferenceType> referenceTypes) {
        if (!content) {
            return
        }

        buildOpenAPISchemaReference(content.schema, referenceTypes)

        buildOpenAPIArraySchemaReference(content.array, referenceTypes)
    }

    private JavaAbstractType resolveFieldTypeRecursive(JavaAbstractType javaAbstractType, List<JavaReferenceType> referenceTypes, DependencyTree dependencyTree = null) {
        if (javaAbstractType instanceof JavaPrimitiveType) {
            return javaAbstractType
        }

        if (javaAbstractType instanceof JavaArrayType
                || javaAbstractType instanceof JavaListType
                || javaAbstractType instanceof JavaSetType) {
            javaAbstractType.typeArgument = resolveFieldTypeRecursive(javaAbstractType.typeArgument, referenceTypes, dependencyTree) as JavaAbstractType

            return javaAbstractType
        }


        if (javaAbstractType instanceof JavaMapType) {
            javaAbstractType = javaAbstractType as JavaMapType

            javaAbstractType.keyTypeArgument = resolveFieldTypeRecursive(javaAbstractType.keyTypeArgument, referenceTypes, dependencyTree) as JavaAbstractType

            javaAbstractType.valueTypeArgument = resolveFieldTypeRecursive(javaAbstractType.valueTypeArgument, referenceTypes, dependencyTree) as JavaAbstractType

            return javaAbstractType
        }

        if (javaAbstractType instanceof JavaTypePlaceholder) { // 什么都不做
            return javaAbstractType
        }

        if (javaAbstractType instanceof JavaGenericType) {
            javaAbstractType = javaAbstractType as JavaGenericType

            def genericType = javaAbstractType.genericType

            if (!genericType) {
                return javaAbstractType
            }

            if (genericType instanceof JavaReferenceType) {
                javaAbstractType.genericType = searchReference(genericType as JavaReferenceType, referenceTypes)

                if (dependencyTree) {
                    dependencyTree = new DependencyTree(qualifiedName: genericType.qualifiedName, qualifiedNames: genericType.possibleQualifiedNames, parent: dependencyTree)
                } else {
                    dependencyTree = new DependencyTree(qualifiedName: genericType.qualifiedName, qualifiedNames: genericType.possibleQualifiedNames)
                }
            } else {
                javaAbstractType.genericType = resolveFieldTypeRecursive(genericType, referenceTypes, dependencyTree)
            }

            def args = []

            javaAbstractType.typeArguments.each {
                args << new JavaTypeArgument(types: [resolveFieldTypeRecursive(it.types.first(), referenceTypes, dependencyTree)])
            }

            javaAbstractType.typeArguments = args

            return javaAbstractType
        }

        if (javaAbstractType instanceof JavaReferenceType) {
            if (dependencyTree) {
                dependencyTree = new DependencyTree(qualifiedName: javaAbstractType.qualifiedName, qualifiedNames: javaAbstractType.possibleQualifiedNames, parent: dependencyTree)
            } else {
                dependencyTree = new DependencyTree(qualifiedName: javaAbstractType.qualifiedName, qualifiedNames: javaAbstractType.possibleQualifiedNames)
            }

            javaAbstractType = searchReference(javaAbstractType, referenceTypes)

            def flag = dependencyTree.isRecursive(javaAbstractType.qualifiedName, javaAbstractType.possibleQualifiedNames)

            if (flag) {
                javaAbstractType.isRecursive = true

                return javaAbstractType
            }

            javaAbstractType = javaAbstractType as JavaReferenceType

            javaAbstractType.extensions = javaAbstractType.extensions.collect {
                return resolveFieldTypeRecursive(it, referenceTypes, dependencyTree)
            } as List<JavaReferenceType>

            return javaAbstractType
        }

        return javaAbstractType
    }

    private static JavaReferenceType searchReference(JavaReferenceType referenceType, List<JavaReferenceType> references) {
        if (referenceType.qualifiedName.startsWith("java") || referenceType.qualifiedName.startsWith("javax")) {
            return referenceType
        }

        def actualReferences = references.findAll {
            (it.qualifiedName in referenceType.possibleQualifiedNames) || (it.qualifiedName == referenceType.qualifiedName)
        }

        // 多模块中出现同名类型时，优选使用本模块中的类型

        def actualReference

        if (actualReferences.size() > 1) {
            actualReference = actualReferences.find {
                it.moduleName == referenceType.moduleName
            }

            !actualReference && (actualReference = actualReferences[0])
        } else {
            actualReference = actualReferences[0]
        }

        if (actualReference) {
            return actualReference
        } else {
            referenceType.notExists = true

            return referenceType
        }
    }
}
