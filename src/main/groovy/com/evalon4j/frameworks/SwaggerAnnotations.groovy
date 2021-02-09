package com.evalon4j.frameworks

import com.evalon4j.frameworks.swagger_annotations.*
import com.github.javaparser.ast.expr.AnnotationExpr

class SwaggerAnnotations {
    // Class Level

    SwaggerDefinition swaggerDefinition = null

    Api api = null

    // Method Level

    ApiOperation apiOperation = null

    ApiImplicitParams apiImplicitParams = null

    List<ApiImplicitParam> apiImplicitParam = []

    ApiResponses apiResponses = null

    List<ApiResponse> apiResponse = []

    ApiParam apiParam = null

    // Model

    ApiModel apiModel = null

    ApiModelProperty apiModelProperty = null

    static SWAGGER_ANNOTATIONS = [
            // Class Level

            SwaggerDefinition,
            Api,
            ApiModel,

            // Method Level

            ApiOperation,
            ApiImplicitParam,
            ApiImplicitParams,
            ApiResponse,
            ApiResponses,

            // Field Level

            ApiParam,
            ApiModelProperty,
    ]

    static final String SWAGGER_PACKAGE = "io.swagger.annotations"

    static boolean hasSwaggerAnnotation(List<AnnotationExpr> annotationExprs, List<String> imports = []) {
        if (!imports.any {
            it.startsWith(SWAGGER_PACKAGE)
        }) {
            return false
        }

        def annotationNames = SWAGGER_ANNOTATIONS.collect {
            it.simpleName
        }

        return annotationExprs && annotationExprs.any {
            return annotationNames.contains(it.nameAsString)
        }
    }
}
