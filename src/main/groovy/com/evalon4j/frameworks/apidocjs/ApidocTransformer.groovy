package com.evalon4j.frameworks.apidocjs


import com.evalon4j.frameworks.apidocjs.pojos.ApidocEndpoint
import com.evalon4j.frameworks.apidocjs.pojos.ApidocField
import com.evalon4j.frameworks.apidocjs.pojos.ApidocFields
import com.evalon4j.frameworks.apidocjs.pojos.ApidocPayload
import com.evalon4j.http.RestfulApiParameterType
import com.evalon4j.java.KeyValueTag
import com.evalon4j.json.*
import org.jsoup.Jsoup

/**
 * transform apidoc json data to evalon4j format
 *
 * @author whitecosm0s_
 */
class ApidocTransformer {
    private static final String PROJECT_TYPE = "APIDOC"

    private static final String PARAMETER_LABEL = "Parameter"

    private static final String HEADER_LABEL = "Header"

    JsonProject transform(ApidocPayload payload) {
        def project = new JsonProject()

        project.projectType = PROJECT_TYPE

        def module = new JsonModule()

        module.restfulApis = buildJsonServices(payload)

        // TODO model

        project.modules << module

        return project
    }

    List<JsonService> buildJsonServices(ApidocPayload payload) {
        Map<String, JsonService> groups = [:]

        payload.data.each { endpoint ->
            if (!endpoint.group) {
                return
            }

            def group = groups[endpoint.group]

            if (!group) {
                def jsonService = new JsonService(serviceName: endpoint.group, summary: endpoint.groupTitle, description: toText(endpoint.groupDescription))

                jsonService.isHttpService = true

                groups[endpoint.group] = jsonService

                group = groups[endpoint.group]
            }

            group.methods << buildJsonMethodFromApidocEndpoint(endpoint)
        }

        return groups.values() as List<JsonService>
    }

    JsonMethod buildJsonMethodFromApidocEndpoint(ApidocEndpoint endpoint) {
        def jsonMethod = new JsonMethod()

        jsonMethod.isHttpService = true

        jsonMethod.methodName = endpoint.name

        jsonMethod.summary = endpoint.title

        jsonMethod.description = toText(endpoint.description)

        jsonMethod.requestMethod = endpoint.type ? endpoint.type.toUpperCase() : "GET" // Default using GET

        jsonMethod.requestPath = endpoint.url

        jsonMethod.fullRequestPath = endpoint.url

        jsonMethod.headers = this.buildHeadersFromApidocEndpoint(endpoint)

        jsonMethod.parameters = this.buildParametersFromApidocEndpoint(endpoint)

        jsonMethod.headers = this.buildHeadersFromApidocEndpoint(endpoint)

        jsonMethod.parameters = this.buildParametersFromApidocEndpoint(endpoint)

        jsonMethod.responses = this.buildResponseFromApidocEndpoint(endpoint)

        jsonMethod.isDeprecated = (endpoint.deprecated != null)

        addVersionTagToJsonMethod(jsonMethod, endpoint)

        return jsonMethod
    }

    void addVersionTagToJsonMethod(JsonMethod jsonMethod, ApidocEndpoint endpoint) {
        if (!endpoint.version) {
            return
        }

        jsonMethod.tags << new KeyValueTag(tagName: "version", tagValue: endpoint.version)
    }

    List<JsonStruct> buildHeadersFromApidocEndpoint(ApidocEndpoint endpoint) {
        def headers = []

        if (!endpoint.header || !endpoint.header.fields) {
            return headers
        }

        endpoint.header.fields[HEADER_LABEL].each { f ->
            headers << buildParameterFromApidocField(f, RestfulApiParameterType.HEADER)
        }

        return headers
    }

    List<JsonStruct> buildParametersFromApidocEndpoint(ApidocEndpoint endpoint) {
        def parameters = []

        if (!endpoint.parameter || !endpoint.parameter.fields) {
            return parameters
        }

        endpoint.parameter.fields.each { entry ->
            if (ApidocParameterUtils.isParameterGroup(entry)) {
                entry.value && entry.value.each { f ->
                    parameters << buildParameterFromApidocField(f, '')
                }
            }

            if (ApidocParameterUtils.isPathGroup(entry)) {
                entry.value && entry.value.each { f ->
                    parameters << buildParameterFromApidocField(f, RestfulApiParameterType.PATH)
                }
            }

            if (ApidocParameterUtils.isQueryGroup(entry)) {
                entry.value && entry.value.each { f ->
                    parameters << buildParameterFromApidocField(f, RestfulApiParameterType.QUERY)
                }
            }

            if (ApidocParameterUtils.isFormGroup(entry)) {
                entry.value && entry.value.each { f ->
                    parameters << buildParameterFromApidocField(f, RestfulApiParameterType.FORM)
                }
            }

            if (ApidocParameterUtils.isMultipartGroup(entry)) {
                entry.value && entry.value.each { f ->
                    parameters << buildParameterFromApidocField(f, RestfulApiParameterType.MULTIPART)
                }
            }

            if (ApidocParameterUtils.isBodyGroup(entry)) {
                def wrapper = new JsonStruct()

                wrapper.fieldName = "Object"

                wrapper.fieldTypeName = "Object"

                wrapper.parameterType = RestfulApiParameterType.BODY

                wrapper.children.addAll(buildJsonStructsFromApidocFields(entry.value))

                parameters << wrapper
            }
        }

        return parameters
    }

    JsonStruct buildParameterFromApidocField(ApidocField f, String parameterType) {
        def parameter = new JsonStruct()

        parameter.fieldName = f.field

        parameter.fieldTypeName = f.type ? f.type : "String" // Default using String

        parameter.parameterType = parameterType

        parameter.fieldSummary = toText(f.description)

        parameter.isRequired = !f.optional // required <-> optional

        // TODO Apidoc Validation

        return parameter
    }

    List<JsonStruct> buildResponseFromApidocEndpoint(ApidocEndpoint endpoint) {
        def responses = []

        responses.addAll(buildResponsesFromApidocFields(endpoint.success))

        responses.addAll(buildResponsesFromApidocFields(endpoint.error))

        return responses
    }

    List<JsonStruct> buildResponsesFromApidocFields(ApidocFields fields) { // WTF
        def responses = []

        if (!fields) {
            return responses
        }

        fields.fields.each { entry ->
            def response = new JsonStruct()

            response.fieldName = entry.key

            response.fieldTypeName = "int" // response code

            response.fieldSummary = entry.key

            response.children.addAll(buildJsonStructsFromApidocFields(entry.value))

            responses << response
        }

        return responses
    }

    List<JsonStruct> buildJsonStructsFromApidocFields(List<ApidocField> fields) { // WTF
        def jsonStructs = []

        Map<String, JsonStruct> references = [:]

        fields.each { f ->
            def splits = f.field.split("\\.").reverse()

            List<JsonStruct> queue = [] // A B C D ... etc

            splits.each { s ->
                def ref = references[s]

                if (!ref) {
                    ref = new JsonStruct()

                    ref.fieldName = s

                    references[s] = ref
                }

                queue << ref
            }

            int length = queue.size()

            queue.eachWithIndex { JsonStruct jsonStruct, int i ->
                if (i == length) {
                    return
                }

                def a = jsonStruct

                def b = queue[i + 1]

                if (!b || a.children.any {
                    it.fieldName === b.fieldName
                }) {
                    return
                }

                a.children << b
            }

            def last = queue.last()

            last.fieldTypeName = f.type ? f.type : "String"

            last.fieldSummary = toText(f.description)

            last.isRequired = !f.optional // required <-> optional

            last.defaultValue = f.defaultValue

            jsonStructs << queue.first()
        }

        return jsonStructs
    }

    String toText(String description) {
        return description ? Jsoup.parse(description).text() : ''
    }
}
