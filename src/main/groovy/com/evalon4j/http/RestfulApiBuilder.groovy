package com.evalon4j.http

import com.evalon4j.Evalon4JTransformer
import com.evalon4j.frameworks.openapi.Parameter
import com.evalon4j.frameworks.spring.HttpStatus
import com.evalon4j.frameworks.spring.MediaType
import com.evalon4j.frameworks.spring.RequestMethod
import com.evalon4j.frameworks.spring.mappings.RequestMapping
import com.evalon4j.frameworks.spring.mappings.SpringMapping
import com.evalon4j.frameworks.swagger.ApiImplicitParam
import com.evalon4j.frameworks.swagger.ApiResponse
import com.evalon4j.java.JavaComponent
import com.evalon4j.java.JavaMethod
import com.evalon4j.java.types.JavaAbstractType
import com.evalon4j.java.types.JavaGenericType
import com.evalon4j.java.types.JavaReferenceType
import com.evalon4j.json.*
import com.evalon4j.transformer.JsonConstraintTransformer
import com.evalon4j.transformer.JsonStructTransformer
import com.evalon4j.utils.JavaTypeUtils
import com.evalon4j.utils.SwaggerDataTypes

class RestfulApiBuilder {
    static JsonService buildRestfulApiGroups(JavaComponent javaComponent, JsonProject jsonProject) {
        def jsonService = new JsonService(javaComponent)

        jsonService.isHttpService = true

        buildJsonServiceFromSwaggerAnnotations(jsonService, javaComponent)

        buildRestfulApiGroupFromOpenApiAnnotations(jsonService, javaComponent)

        buildJsonServiceFromSpringAnnotations(jsonService, javaComponent)

        buildJsonServiceFromJaxRSAnnotations(jsonService, javaComponent)

        jsonService.basePath = formatRequestPath(jsonService.basePath)

        javaComponent.methods.each { javaMethod ->
            try {
                def jsonMethod = buildRestfulApi(javaComponent, javaMethod)

                if (!jsonMethod) {
                    return
                }

                jsonMethod.basePath = formatRequestPath(jsonMethod.basePath)

                jsonMethod.requestPath = formatRequestPath(jsonMethod.requestPath)

                jsonMethod.fullRequestPath = jsonMethod.basePath + jsonMethod.requestPath

                jsonService.methods << jsonMethod
            } catch (Exception e) {
                jsonProject.errors << new JsonError(e)
            }
        }

        return jsonService.methods ? jsonService : null
    }

    private static buildRestfulApiFromExtension(JavaComponent javaComponent, JsonService jsonService, JavaAbstractType extension) {
        if (extension instanceof JavaReferenceType) {
            extension.methods.each { javaMethod ->
                def jsonMethod = buildRestfulApi(javaComponent, javaMethod)

                if (!jsonMethod) {
                    return
                }

                jsonService.methods << jsonMethod
            }

            extension.extensions.each {
                buildRestfulApiFromExtension(javaComponent, jsonService, it)
            }
        }

        if (extension instanceof JavaGenericType) {
            def actualExtension = extension.build()

            actualExtension.methods.each { javaMethod ->
                def jsonMethod = buildRestfulApi(javaComponent, javaMethod)

                if (!jsonMethod) {
                    return
                }

                jsonService.methods << jsonMethod
            }

            actualExtension.extensions.each {
                buildRestfulApiFromExtension(javaComponent, jsonService, it)
            }
        }
    }

    private static buildJsonServiceFromSpringAnnotations(JsonService jsonService, JavaComponent javaComponent) {
        if (!javaComponent.springAnnotations) {
            return
        }

        def springAnnotations = javaComponent.springAnnotations

        if (springAnnotations.restController) {
            if (javaComponent.extensions) { // 处理抽象Controller
                javaComponent.extensions.each { extension ->
                    if (extension instanceof JavaGenericType) {
                        def actualType = extension.build()

                        if (actualType.notExists) {
                            return
                        }

                        actualType && actualType.methods.each {javaMethod ->
                            def api = buildRestfulApi(javaComponent, javaMethod)

                            api && (jsonService.methods << api)
                        }
                    }

                    if (extension instanceof JavaReferenceType) {
                        if (extension.notExists) {
                            return
                        }

                        extension.methods.each { javaMethod ->
                            def api = buildRestfulApi(javaComponent, javaMethod)

                            api && (jsonService.methods << api)
                        }
                    }
                }
            }

            if (springAnnotations.requestMapping) { // 处理RequestMapping
                def requestMapping = springAnnotations.requestMapping

                if (requestMapping.name) {
                    jsonService.summary = requestMapping.name
                }

                if (requestMapping.value) {
                    jsonService.basePath = requestMapping.value[0]
                }

                if (requestMapping.path) {
                    jsonService.basePath = requestMapping.path[0]
                }
            }
        }

        if (springAnnotations.feignClient) {
            def feignClient = springAnnotations.feignClient

            feignClient.name && (jsonService.summary = feignClient.name)

            feignClient.value && (jsonService.summary = feignClient.value)

            feignClient.path && (jsonService.basePath = feignClient.path)

            // TODO url
        }
    }

    private static buildJsonServiceFromJaxRSAnnotations(JsonService jsonService, JavaComponent javaComponent) {
        if (!javaComponent.jaxRSAnnotations) {
            return
        }

        def jaxRSAnnotations = javaComponent.jaxRSAnnotations

        def applicationPath = jaxRSAnnotations.applicationPath

        def path = jaxRSAnnotations.path

        applicationPath && (jsonService.basePath = applicationPath.value)

        path && (jsonService.basePath = path.value)
    }

    private static buildJsonServiceFromSwaggerAnnotations(JsonService jsonService, JavaComponent javaComponent) {
        if (!javaComponent.swaggerAnnotations) {
            return
        }

        def swaggerAnnotations = javaComponent.swaggerAnnotations

        def api = swaggerAnnotations.api

        if (!api) {
            return
        }

        if (api.value) {
            api.value.startsWith("/") && (jsonService.basePath = api.value) // Legacy Support

            !api.value.startsWith("/") && (jsonService.summary = api.value)
        }

        if (api.description) {
            jsonService.description = api.description
        }

        if (api.tags) {
            jsonService.summary = api.tags[0]
        }

        if (api.basePath) {
            jsonService.basePath = api.basePath
        }

        if (api.hidden) {
            jsonService.isHidden = api.hidden
        }
    }

    private static buildRestfulApiGroupFromOpenApiAnnotations(JsonService jsonService, JavaComponent javaComponent) {
        // 忽略，OpenAPI没有服务级相关的注解
    }

    private static JsonMethod buildRestfulApi(JavaComponent javaComponent, JavaMethod javaMethod) {
        if (!Evalon4JTransformer.isRestfulApi(javaComponent, javaMethod)) {
            return null
        }

        def jsonMethod = new JsonMethod(javaMethod)

        jsonMethod.isHttpService = true

        buildRestfulApiFromParentSwaggerAnnotations(jsonMethod, javaComponent)

        buildRestfulApiFromParentOpenAPIAnnotations(jsonMethod, javaComponent)

        buildRestfulApiFromParentSpringAnnotations(jsonMethod, javaComponent)

        buildRestfulApiFromParentJaxRSAnnotations(jsonMethod, javaComponent)

        buildRestfulApiFromSwaggerAnnotations(jsonMethod, javaMethod)

        buildRestfulApiFromOpenAPIAnnotations(jsonMethod, javaMethod)

        buildRestfulApiFromSpringAnnotations(jsonMethod, javaMethod)

        buildRestfulApiFromJaxRSAnnotations(jsonMethod, javaMethod)

        jsonMethod.parameterConstraints = JsonConstraintTransformer.transform(jsonMethod.parameters)

        buildRestfulApiConsumes(jsonMethod)

        buildRestfulApiRequired(jsonMethod)

        return jsonMethod
    }

    private static buildRestfulApiFromParentSpringAnnotations(JsonMethod jsonMethod, JavaComponent javaComponent) {
        if (!javaComponent.springAnnotations) {
            return
        }

        def springAnnotations = javaComponent.springAnnotations

        if (!springAnnotations.requestMapping) {
            return
        }

        def requestMapping = springAnnotations.requestMapping

        requestMapping.value && (jsonMethod.basePath = requestMapping.value[0])

        requestMapping.path && (jsonMethod.basePath = requestMapping.path[0])

        jsonMethod.consumes.addAll(requestMapping.consumes)

        jsonMethod.produces.addAll(requestMapping.produces)
    }

    private static buildRestfulApiFromParentJaxRSAnnotations(JsonMethod jsonMethod, JavaComponent javaComponent) {
        if (!javaComponent.jaxRSAnnotations) {
            return
        }

        def jaxRSAnnotations = javaComponent.jaxRSAnnotations

        def applicationPath = jaxRSAnnotations.applicationPath

        def path = jaxRSAnnotations.path

        def consumes = jaxRSAnnotations.consumes

        def produces = jaxRSAnnotations.produces

        applicationPath && (jsonMethod.basePath = applicationPath.value)

        path && (jsonMethod.basePath = path.value)

        consumes && (jsonMethod.consumes = consumes.value)

        produces && (jsonMethod.produces = produces.value)
    }

    private static buildRestfulApiFromParentSwaggerAnnotations(JsonMethod jsonMethod, JavaComponent javaComponent) {
        if (!javaComponent.swaggerAnnotations) {
            return
        }

        def swaggerAnnotations = javaComponent.swaggerAnnotations

        def api = swaggerAnnotations.api

        if (!api) {
            return
        }

        api.basePath && (jsonMethod.basePath = api.basePath)
    }

    private static buildRestfulApiFromParentOpenAPIAnnotations(JsonMethod jsonMethod, JavaComponent javaComponent) {
        // ignore
    }

    private static buildRestfulApiFromSpringAnnotations(JsonMethod jsonMethod, JavaMethod javaMethod) {
        if (!javaMethod.springAnnotations) {
            return
        }

        def springAnnotations = javaMethod.springAnnotations

        def requestMapping = springAnnotations.requestMapping

        def getMapping = springAnnotations.getMapping

        def postMapping = springAnnotations.postMapping

        def putMapping = springAnnotations.putMapping

        def deleteMapping = springAnnotations.deleteMapping

        def patchMapping = springAnnotations.patchMapping

        requestMapping && buildRestfulApiFromSpringMapping(jsonMethod, requestMapping)

        getMapping && (jsonMethod.requestMethod = RequestMethod.GET.toString()) && buildRestfulApiFromSpringMapping(jsonMethod, getMapping)

        postMapping && (jsonMethod.requestMethod = RequestMethod.POST.toString()) && buildRestfulApiFromSpringMapping(jsonMethod, postMapping)

        putMapping && (jsonMethod.requestMethod = RequestMethod.PUT.toString()) && buildRestfulApiFromSpringMapping(jsonMethod, putMapping)

        deleteMapping && (jsonMethod.requestMethod = RequestMethod.DELETE.toString()) && buildRestfulApiFromSpringMapping(jsonMethod, deleteMapping)

        patchMapping && (jsonMethod.requestMethod = RequestMethod.PATCH.toString()) && buildRestfulApiFromSpringMapping(jsonMethod, patchMapping)

        buildRestfulApiParametersFromSpringAnnotations(jsonMethod, javaMethod)

        buildRestfulApiResponseFromJavaMethod(jsonMethod, javaMethod)

        requestMapping && handleParametersInSpringMapping(jsonMethod, requestMapping)

        getMapping && handleParametersInSpringMapping(jsonMethod, getMapping)

        postMapping && handleParametersInSpringMapping(jsonMethod, postMapping)

        putMapping && handleParametersInSpringMapping(jsonMethod, putMapping)

        deleteMapping && handleParametersInSpringMapping(jsonMethod, deleteMapping)

        patchMapping && handleParametersInSpringMapping(jsonMethod, patchMapping)

        return jsonMethod
    }

    private static buildRestfulApiFromSpringMapping(JsonMethod jsonMethod, SpringMapping springMapping) {
        if (!springMapping) {
            return
        }

        springMapping.name && (jsonMethod.summary = springMapping.name)

        springMapping.value && (jsonMethod.requestPath = springMapping.value[0])

        springMapping.path && (jsonMethod.requestPath = springMapping.path[0])

        springMapping.consumes && (jsonMethod.consumes = springMapping.consumes)

        springMapping.produces && (jsonMethod.produces = springMapping.produces)

        if (springMapping instanceof RequestMapping) {
            if (springMapping.method) {
                jsonMethod.requestMethod = springMapping.method.first().toString()
            }
        }
    }

    private static handleParametersInSpringMapping(JsonMethod jsonMethod, SpringMapping springMapping) {
        springMapping.params.each { paramStr ->
            def splits = paramStr.split("=")

            if (splits.length !== 2) {
                return
            }

            def paramName = splits[0].trim()

            def paramValue = splits[1].trim()

            def parameter = jsonMethod.parameters.find {
                return it.fieldName === paramName
            }

            parameter && (parameter.defaultValue = paramValue)
        }

        springMapping.headers.each { headerStr ->
            def splits = headerStr.split("=")

            if (splits.length !== 2) {
                return
            }

            def headerName = splits[0].trim()

            def headerValue = splits[1].trim()

            def header = jsonMethod.parameters.find {
                return it.fieldName === headerName
            }

            header && (header.defaultValue = headerValue)
        }
    }

    private static buildRestfulApiParametersFromSpringAnnotations(JsonMethod jsonMethod, JavaMethod javaMethod) {
        javaMethod.parameters.each { parameter ->
            def jsonStruct = JsonStructTransformer.transformJavaFieldToJsonStruct(parameter)

            // 基本类型绑定

            if (JavaTypeUtils.isStringType(parameter.fieldType)
                    || JavaTypeUtils.isNumberType(parameter.fieldType)
                    || JavaTypeUtils.isBooleanType(parameter.fieldType)
                    || JavaTypeUtils.isDatetimeType(parameter.fieldType)
                    || JavaTypeUtils.isFileType(parameter.fieldType)) {
                if (jsonMethod.requestMethod == "GET") {
                    jsonStruct.parameterType = RestfulApiParameterType.QUERY
                } else {
                    jsonStruct.parameterType = RestfulApiParameterType.FORM
                }

                if (!parameter.springAnnotations) {
                    jsonMethod.parameters << jsonStruct

                    return
                }
            }

            def springAnnotations = parameter.springAnnotations

            if (!springAnnotations) {
                return
            }

            def pathVariable = springAnnotations.pathVariable

            def requestParam = springAnnotations.requestParam

            def requestHeader = springAnnotations.requestHeader

            def cookieValue = springAnnotations.cookieValue

            def requestPart = springAnnotations.requestPart

            def requestBody = springAnnotations.requestBody

            def modelAttribute = springAnnotations.modelAttribute

            if (pathVariable) {
                pathVariable.value && (jsonStruct.fieldName = pathVariable.value)

                pathVariable.name && (jsonStruct.fieldName = pathVariable.name)

                jsonStruct.parameterType = RestfulApiParameterType.PATH

                jsonMethod.parameters << jsonStruct
            }

            if (requestParam) {
                requestParam.value && (jsonStruct.fieldName = requestParam.value)

                requestParam.name && (jsonStruct.fieldName = requestParam.name)

                // RequestParam在GET请求中作为Query参数，在其它请求中作为Form参数

                if (jsonMethod.requestMethod == RequestMethod.GET.toString()) {
                    jsonStruct.parameterType = RestfulApiParameterType.QUERY
                } else {
                    jsonStruct.parameterType = RestfulApiParameterType.FORM
                }

                jsonStruct.isRequired = requestParam.required

                jsonStruct.defaultValue = requestParam.defaultValue

                jsonMethod.parameters << jsonStruct
            }

            if (requestHeader) {
                requestHeader.value && (jsonStruct.fieldName = requestHeader.value)

                requestHeader.name && (jsonStruct.fieldName = requestHeader.name)

                jsonStruct.parameterType = RestfulApiParameterType.HEADER

                jsonStruct.isRequired = requestHeader.required

                jsonStruct.defaultValue = requestHeader.defaultValue

                jsonMethod.headers << jsonStruct
            }

            if (cookieValue) {
                cookieValue.value && (jsonStruct.fieldName = cookieValue.value)

                cookieValue.name && (jsonStruct.fieldName = cookieValue.name)

                jsonStruct.parameterType = RestfulApiParameterType.COOKIE

                jsonStruct.isRequired = cookieValue.required

                jsonStruct.defaultValue = cookieValue.defaultValue

                jsonMethod.cookies << jsonStruct
            }

            if (requestPart) {
                requestPart.value && (jsonStruct.fieldName = requestPart.value)

                requestPart.name && (jsonStruct.fieldName = requestPart.name)

                jsonStruct.parameterType = RestfulApiParameterType.MULTIPART

                jsonStruct.isRequired = requestPart.required

                jsonMethod.parameters << jsonStruct
            }

            if (requestBody) {
                jsonStruct.parameterType = RestfulApiParameterType.BODY

                jsonStruct.isRequired = requestBody.required

                jsonMethod.parameters << jsonStruct
            }

            if (modelAttribute) {
                modelAttribute.name && (jsonStruct.fieldName = modelAttribute.name)

                modelAttribute.value && (jsonStruct.fieldName = modelAttribute.value)

                jsonStruct.parameterType = RestfulApiParameterType.FORM

                jsonMethod.parameters << jsonStruct
            }
        }
    }

    private static buildRestfulApiFromJaxRSAnnotations(JsonMethod jsonMethod, JavaMethod javaMethod) {
        if (!javaMethod.jaxRSAnnotations) {
            return
        }

        def jaxRSAnnotations = javaMethod.jaxRSAnnotations

        def path = jaxRSAnnotations.path

        if (!path) { // jax-rs 接口必须存在 @Path 注解
            return
        }

        jsonMethod.requestPath = path.value

        jaxRSAnnotations.get && (jsonMethod.requestMethod = RestfulApiRequestMethod.GET)

        jaxRSAnnotations.post && (jsonMethod.requestMethod = RestfulApiRequestMethod.POST)

        jaxRSAnnotations.put && (jsonMethod.requestMethod = RestfulApiRequestMethod.PUT)

        jaxRSAnnotations.delete && (jsonMethod.requestMethod = RestfulApiRequestMethod.DELETE)

        jaxRSAnnotations.options && (jsonMethod.requestMethod = RestfulApiRequestMethod.OPTIONS)

        jaxRSAnnotations.patch && (jsonMethod.requestMethod = RestfulApiRequestMethod.PATCH)

        buildRestfulApiParametersFromJaxRSAnnotations(jsonMethod, javaMethod)

        buildRestfulApiResponseFromJavaMethod(jsonMethod, javaMethod)
    }

    private static buildRestfulApiParametersFromJaxRSAnnotations(JsonMethod jsonMethod, JavaMethod javaMethod) {
        javaMethod.parameters.each { parameter ->
            def jsonStruct = JsonStructTransformer.transformJavaFieldToJsonStruct(parameter)

            if (parameter.jaxRSAnnotations) {
                def jaxRSAnnotations = parameter.jaxRSAnnotations

                def pathParam = jaxRSAnnotations.pathParam

                def queryParam = jaxRSAnnotations.queryParam

                def formParam = jaxRSAnnotations.formParam

                def headerParam = jaxRSAnnotations.headerParam

                def cookieParam = jaxRSAnnotations.cookieParam

                def defaultValue = jaxRSAnnotations.defaultValue

                if (pathParam) {
                    pathParam.value && (parameter.fieldName = pathParam.value)

                    jsonStruct.parameterType = RestfulApiParameterType.PATH

                    jsonMethod.parameters << jsonStruct
                }

                if (queryParam) {
                    queryParam.value && (parameter.fieldName = queryParam.value)

                    jsonStruct.parameterType = RestfulApiParameterType.QUERY

                    jsonMethod.parameters << jsonStruct
                }

                if (formParam) {
                    formParam.value && (parameter.fieldName = formParam.value)

                    jsonStruct.parameterType = RestfulApiParameterType.FORM

                    jsonMethod.parameters << jsonStruct
                }

                if (headerParam) {
                    headerParam.value && (parameter.fieldName = headerParam.value)

                    jsonStruct.parameterType = RestfulApiParameterType.HEADER

                    jsonMethod.headers << jsonStruct
                }

                if (cookieParam) {
                    cookieParam.value && (parameter.fieldName = cookieParam.value)

                    jsonStruct.parameterType = RestfulApiParameterType.COOKIE

                    jsonMethod.cookies << jsonStruct
                }

                if (defaultValue) {
                    jsonStruct.defaultValue = defaultValue.value
                }
            } else { // 其它一律算body
                if (jsonStruct.isStructType && !jsonStruct.notExists) {
                    jsonStruct.parameterType = RestfulApiParameterType.BODY

                    jsonMethod.parameters << jsonStruct
                }
            }
        }
    }

    private static buildRestfulApiResponseFromJavaMethod(JsonMethod jsonMethod, JavaMethod javaMethod) {
        def existsResponse = jsonMethod.responses.find {
            try {
                int code = Integer.valueOf(it.fieldName)

                def series = HttpStatus.Series.valueOf(code)

                return series == HttpStatus.Series.SUCCESSFUL
            } catch (Exception ignore) {
                return false
            }
        }

        if (existsResponse && existsResponse.children) { // 如果已存在返回体，则跳过
            return
        }

        if (!javaMethod.response) {
            jsonMethod.responses << new JsonStruct(
                    fieldName: "200",
                    fieldTypeName: "int",
                    fieldSummary: "OK")

            return
        }

        def children = []

        if (javaMethod.response) {
            children.addAll(JsonStructTransformer.transformJavaAbstractTypeToJsonStruct(javaMethod.response))
        }

        if (existsResponse) {
            existsResponse.children.addAll(children)
        } else {
            jsonMethod.responses << new JsonStruct(
                    fieldName: "200",
                    fieldTypeName: "int",
                    fieldSummary: "OK",
                    children: children)
        }
    }

    private static buildRestfulApiFromSwaggerAnnotations(JsonMethod jsonMethod, JavaMethod javaMethod) {
        if (!javaMethod.swaggerAnnotations) {
            return
        }

        def swaggerAnnotations = javaMethod.swaggerAnnotations

        def apiOperation = swaggerAnnotations.apiOperation

        if (!apiOperation) {
            return
        }

        if (apiOperation.value) {
            def value = apiOperation.value

            value.startsWith("/") && (jsonMethod.requestPath = value) // Legacy Support

            !value.startsWith("/") && (jsonMethod.summary = value)
        }

        if (apiOperation.tags) {
            jsonMethod.summary = apiOperation.tags.first()
        }

        apiOperation.httpMethod && (jsonMethod.requestMethod = apiOperation.httpMethod)

        apiOperation.consumes && (jsonMethod.consumes = [apiOperation.consumes])

        apiOperation.produces && (jsonMethod.produces = [apiOperation.produces])

        buildRestfulApiParametersFromSwaggerAnnotations(jsonMethod, javaMethod)

        buildRestfulApiResponseFromSwaggerAnnotations(jsonMethod, javaMethod)
    }

    private static buildRestfulApiParametersFromSwaggerAnnotations(JsonMethod jsonMethod, JavaMethod javaMethod) {
        def swaggerAnnotations = javaMethod.swaggerAnnotations

        List<ApiImplicitParam> apiImplicitParams = []

        if (swaggerAnnotations.apiImplicitParams) {
            apiImplicitParams.addAll(swaggerAnnotations.apiImplicitParams.value)
        }

        if (swaggerAnnotations.apiImplicitParam) {
            apiImplicitParams.addAll(swaggerAnnotations.apiImplicitParam)
        }

        if (apiImplicitParams) {
            apiImplicitParams.each { apiImplicitParam ->
                def children = []

                if (apiImplicitParam.dataTypeClass) {
                    def jsonStruct = JsonStructTransformer.transformJavaAbstractTypeToJsonStruct(apiImplicitParam.dataTypeClass)

                    if (jsonStruct.isStructType) {
                        children << jsonStruct
                    }
                }

                String fieldTypeName = ""

                if (apiImplicitParam.dataTypeClass) {
                    fieldTypeName = apiImplicitParam.dataTypeClass.simpleName
                }

                if (apiImplicitParam.dataType) {
                    fieldTypeName = apiImplicitParam.dataType
                }

                if (apiImplicitParam.type) {
                    fieldTypeName = apiImplicitParam.type
                }

                jsonMethod.parameters << new JsonStruct(
                        fieldName: apiImplicitParam.name,
                        fieldTypeName: fieldTypeName,
                        swaggerDataType: JavaTypeUtils.getSwaggerDataType(fieldTypeName),
                        swaggerDataFormat: JavaTypeUtils.getSwaggerDataFormat(fieldTypeName),
                        parameterType: apiImplicitParam.paramType,
                        fieldSummary: apiImplicitParam.value,
                        defaultValue: apiImplicitParam.defaultValue,
                        children: children
                )
            }

            return
        }

        javaMethod.parameters.each { parameter ->
            def jsonStruct = jsonMethod.parameters.find {
                it.fieldName == parameter.fieldName
            }

            if (!jsonStruct) {
                jsonStruct = JsonStructTransformer.transformJavaFieldToJsonStruct(parameter)
            }

            if (jsonStruct.swaggerAnnotations && jsonStruct.swaggerAnnotations.apiParam) {
                def apiParam = jsonStruct.swaggerAnnotations.apiParam

                apiParam.name && (jsonStruct.fieldName = apiParam.name)

                apiParam.value && (jsonStruct.fieldSummary = apiParam.value)

                jsonStruct.isRequired = apiParam.required

                apiParam.defaultValue && (jsonStruct.defaultValue = apiParam.defaultValue)

                jsonMethod.parameters << jsonStruct
            }
        }
    }

    private static buildRestfulApiResponseFromSwaggerAnnotations(JsonMethod jsonMethod, JavaMethod javaMethod) {
        def swaggerAnnotations = javaMethod.swaggerAnnotations

        def apiOperation = swaggerAnnotations.apiOperation

        if (apiOperation && apiOperation.response) {
            def jsonStruct = new JsonStruct(fieldName: "200", fieldTypeName: "int")

            jsonStruct.children << JsonStructTransformer.transformJavaAbstractTypeToJsonStruct(apiOperation.response)

            jsonMethod.responses << jsonStruct
        }

        List<ApiResponse> apiResponses = []

        if (swaggerAnnotations.apiResponses) {
            apiResponses.addAll(swaggerAnnotations.apiResponses.value)
        }

        if (swaggerAnnotations.apiResponse) {
            apiResponses.addAll(swaggerAnnotations.apiResponse)
        }

        apiResponses && apiResponses.each { apiResponse ->
            def jsonStruct = new JsonStruct(
                    fieldName: apiResponse.code,
                    fieldTypeName: "int",
                    fieldSummary: apiResponse.message ? apiResponse.message : (HttpStatus.resolve(apiResponse.code).reasonPhrase)
            )

            if (apiResponse.response) {
                jsonStruct.children << JsonStructTransformer.transformJavaAbstractTypeToJsonStruct(apiResponse.response)
            }

            if (apiResponse.responseHeaders) {
                def responseHeader = new JsonStruct(
                        fieldName: apiResponse.code,
                        fieldTypeName: "int",
                        fieldSummary: apiResponse.message ? apiResponse.message : (HttpStatus.resolve(apiResponse.code).reasonPhrase))

                apiResponse.responseHeaders.each { header ->
                    responseHeader.children << new JsonStruct(
                            fieldName: header.name,
                            fieldTypeName: "String",
                            fieldSummary: header.description)
                }

                jsonMethod.responseHeaders << responseHeader
            }

            jsonMethod.responses << jsonStruct
        }
    }

    private static buildRestfulApiFromOpenAPIAnnotations(JsonMethod jsonMethod, JavaMethod javaMethod) {
        if (!javaMethod.openAPIAnnotations) {
            return
        }

        def openAPIAnnotations = javaMethod.openAPIAnnotations

        if (!openAPIAnnotations.operation) {
            return
        }

        def operation = openAPIAnnotations.operation

        operation.method && (jsonMethod.requestMethod = operation.method)

        def summary = operation.summary

        summary && summary.startsWith("/") && (jsonMethod.requestPath = summary) // Legacy Support

        summary && !summary.startsWith("/") && (jsonMethod.summary = operation.summary)

        if (operation.tags) {
            //TODO
        }

        operation.description && (jsonMethod.description = operation.description)

        buildRestfulApiParametersFromOpenAPIAnnotations(jsonMethod, javaMethod)

        buildRestfulApiResponsesFromOpenAPIAnnotations(jsonMethod, javaMethod)
    }

    private static buildRestfulApiParametersFromOpenAPIAnnotations(JsonMethod jsonMethod, JavaMethod javaMethod) {
        def openAPIAnnotations = javaMethod.openAPIAnnotations

        def operation = openAPIAnnotations.operation

        operation.parameters.each { p ->
            def jsonStruct = RestfulApiHelper.transformOpenAPIParameterToJsonStruct(p)

            jsonStruct && jsonMethod.parameters << jsonStruct
        }

        if (operation.requestBody) {
            def requestBody = operation.requestBody

            def jsonStruct = null

            if (requestBody.content) {
                def content = requestBody.content.first()

                jsonStruct = RestfulApiHelper.transformContentToJsonStruct(content)
            }

            if (!jsonStruct) {
                return
            }

            jsonStruct.fieldName = jsonStruct.fieldTypeName

            jsonStruct.parameterType = RestfulApiParameterType.BODY

            jsonMethod.parameters << jsonStruct
        }

        List<Parameter> parameters = []

        if (openAPIAnnotations.parameters) {
            parameters.addAll(openAPIAnnotations.parameters.value)
        }

        if (openAPIAnnotations.parameter) {
            parameters.addAll(openAPIAnnotations.parameter)
        }

        parameters.each { p ->
            def jsonStruct = RestfulApiHelper.transformOpenAPIParameterToJsonStruct(p)

            jsonStruct && jsonMethod.parameters << jsonStruct
        }
    }

    private static buildRestfulApiResponsesFromOpenAPIAnnotations(JsonMethod jsonMethod, JavaMethod javaMethod) {
        def openAPIAnnotations = javaMethod.openAPIAnnotations

        def operation = openAPIAnnotations.operation

        List<com.evalon4j.frameworks.openapi.responses.ApiResponse> apiResponses = []

        if (operation.responses) {
            apiResponses.addAll(operation.responses)
        }

        if (openAPIAnnotations.apiResponses) {
            apiResponses.addAll(openAPIAnnotations.apiResponses.value)
        }

        if (openAPIAnnotations.apiResponse) {
            apiResponses.addAll(openAPIAnnotations.apiResponse)
        }

        apiResponses.each { apiResponse ->
            def jsonStruct = new JsonStruct()

            apiResponse.description && (jsonStruct.fieldSummary = apiResponse.description)

            apiResponse.responseCode && (jsonStruct.fieldName = apiResponse.responseCode)

            jsonStruct.fieldTypeName = "int"

            // links 不支持

            if (apiResponse.content) {
                apiResponse.content.each { content ->
                    jsonMethod.produces << content.mediaType
                }

                def content = apiResponse.content.first() // 仅支持第一个

                if (content?.schema?.implementation) {
                    def child = JsonStructTransformer.transformJavaAbstractTypeToJsonStruct(content.schema.implementation)

                    content.schema.name && (child.fieldSummary = content.schema.name)

                    content.schema.title && (child.fieldSummary = content.schema.title)

                    jsonStruct.children << child
                }

                if (content?.array?.schema?.implementation) {
                    def child = new JsonStruct()

                    def implementation = JsonStructTransformer.transformJavaAbstractTypeToJsonStruct(content.array.schema.implementation)

                    child.fieldName = "List<${implementation.fieldTypeName}>".toString()

                    child.fieldTypeName = "List<${implementation.fieldTypeName}>".toString()

                    child.children << implementation

                    jsonStruct.children << child
                }
            }

            if (apiResponse.headers) { // headers
                def responseHeader = new JsonStruct(
                        fieldName: apiResponse.responseCode,
                        fieldTypeName: "int",
                        fieldSummary: apiResponse.description)

                apiResponse.headers.each { header ->
                    responseHeader.children << new JsonStruct(
                            fieldName: header.name,
                            fieldTypeName: "String",
                            fieldSummary: header.description)
                }

                jsonMethod.responseHeaders << responseHeader
            }

            // encoding 不支持

            apiResponse.content.each { content ->
                jsonMethod.produces << content.mediaType
            }

            jsonMethod.responses << jsonStruct
        }

        // 不支持其它位置的注解
    }

    private static buildRestfulApiConsumes(JsonMethod jsonMethod) {
        if (jsonMethod.parameters.any {
            it.parameterType == RestfulApiParameterType.FORM
        }) {
            !jsonMethod.consumes && (jsonMethod.consumes = [MediaType.APPLICATION_FORM_URLENCODED_VALUE])
        }

        if (jsonMethod.parameters.any {
            it.parameterType == RestfulApiParameterType.MULTIPART || it.swaggerDataType == SwaggerDataTypes.FILE
        }) {
            !jsonMethod.consumes && (jsonMethod.consumes = [MediaType.MULTIPART_FORM_DATA_VALUE])
        }

        if (jsonMethod.parameters.any {
            it.parameterType == RestfulApiParameterType.BODY
        }) {
            !jsonMethod.consumes && (jsonMethod.consumes = [MediaType.APPLICATION_JSON_VALUE])
        }

        jsonMethod.consumes && (jsonMethod.consumes = jsonMethod.consumes.unique())

        jsonMethod.produces && (jsonMethod.produces = jsonMethod.produces.unique())
    }

    private static buildRestfulApiRequired(JsonMethod jsonMethod) {
        jsonMethod.parameters.each { p ->
            if (p.parameterType == RestfulApiParameterType.PATH) {
                p.isRequired = true
            }
        }
    }

    private static String formatRequestPath(String requestPath) {
        if (!requestPath) {
            return ""
        }

        if (!requestPath.startsWith("/")) {
            requestPath = "/" + requestPath
        }

        if (requestPath.endsWith("/")) {
            requestPath = requestPath.substring(0, requestPath.length() - 1)
        }

        return requestPath
    }
}
