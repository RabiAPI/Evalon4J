package com.evalon4j.frameworks.apidocjs

import com.evalon4j.frameworks.apidocjs.pojos.ApidocEndpoint
import com.evalon4j.frameworks.apidocjs.pojos.ApidocField

/**
 * comment
 *
 * @author whitecosm0s_
 */
class ApidocParameterUtils {
    private static final String PARAMETER_GROUP = "parameter"

    private static final String PATH_GROUP = "path"

    private static final String QUERY_GROUP = "query"

    private static final String FORM_GROUP = "form"

    private static final String MULTIPART_GROUP = "multipart"

    private static final String BODY_GROUP = "body"

    private static final String JSON_GROUP = "json"

    static isParameterGroup(Map.Entry<String, List<ApidocField>> entry) {
        return entry.key.toLowerCase() == PARAMETER_GROUP
    }

    static isParameterPathField(ApidocEndpoint endpoint, ApidocField field) {
        //TODO
    }

    static isParameterQueryField(ApidocEndpoint endpoint, ApidocField field) {
        //TODO
    }

    static isPathGroup(Map.Entry<String, List<ApidocField>> entry) {
        return entry.key.toLowerCase() == PATH_GROUP
    }

    static isQueryGroup(Map.Entry<String, List<ApidocField>> entry) {
        return entry.key.toLowerCase() == QUERY_GROUP
    }

    static isFormGroup(Map.Entry<String, List<ApidocField>> entry) {
        return entry.key.toLowerCase() == FORM_GROUP
    }

    static isMultipartGroup(Map.Entry<String, List<ApidocField>> entry) {
        return entry.key.toLowerCase() == MULTIPART_GROUP
    }

    static isBodyGroup(Map.Entry<String, List<ApidocField>> entry) {
        return entry.key.toLowerCase() == BODY_GROUP || entry.key.toLowerCase() == JSON_GROUP
    }
}
