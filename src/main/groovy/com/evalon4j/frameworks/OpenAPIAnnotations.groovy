package com.evalon4j.frameworks

import com.evalon4j.frameworks.openapi_annotations.OpenAPIDefinition
import com.evalon4j.frameworks.openapi_annotations.Operation
import com.evalon4j.frameworks.openapi_annotations.Parameter
import com.evalon4j.frameworks.openapi_annotations.Parameters
import com.evalon4j.frameworks.openapi_annotations.responses.ApiResponse
import com.evalon4j.frameworks.openapi_annotations.responses.ApiResponses
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
