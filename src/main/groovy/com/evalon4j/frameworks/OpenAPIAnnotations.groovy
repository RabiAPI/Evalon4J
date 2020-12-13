package com.evalon4j.frameworks

import com.evalon4j.frameworks.openapi.OpenAPIDefinition
import com.evalon4j.frameworks.openapi.Operation
import com.evalon4j.frameworks.openapi.Parameter
import com.evalon4j.frameworks.openapi.Parameters
import com.evalon4j.frameworks.openapi.responses.ApiResponse
import com.evalon4j.frameworks.openapi.responses.ApiResponses
import com.github.javaparser.ast.expr.AnnotationExpr

class OpenAPIAnnotations {
    Operation operation = null

    Parameters parameters = null

    List<Parameter> parameter = []

    ApiResponses apiResponses = null

    List<ApiResponse> apiResponse = []

    OpenAPIDefinition openAPIDefinition = null

    static OPENAPI_ANNOTATIONS = [
            Operation,
            Parameters,
            ApiResponses,
            OpenAPIDefinition,
    ]

    static final String OPENAPI_PACKAGE = "io.swagger.v3"

    static boolean hasOpenAPIAnnotations(List<AnnotationExpr> annotationExprs, List<String> imports = []) {
        if (!imports.any {
            it.startsWith(OPENAPI_PACKAGE)
        }) {
            return false
        }

        def annotationNames = OPENAPI_ANNOTATIONS.collect {
            it.simpleName
        }

        return annotationExprs && annotationExprs.any {
            return annotationNames.contains(it.nameAsString)
        }
    }
}
