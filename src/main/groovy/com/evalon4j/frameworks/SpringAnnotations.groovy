package com.evalon4j.frameworks

import com.evalon4j.frameworks.spring.ResponseStatus
import com.evalon4j.frameworks.spring.controller.ControllerAdvice
import com.evalon4j.frameworks.spring.RestController
import com.evalon4j.frameworks.spring.Validated
import com.evalon4j.frameworks.spring.controller.ExceptionHandler
import com.evalon4j.frameworks.spring.mappings.*
import com.evalon4j.frameworks.spring.parameters.*
import com.evalon4j.frameworks.spring_cloud.FeignClient
import com.github.javaparser.ast.expr.AnnotationExpr

class SpringAnnotations {
    // Class Level

    RestController restController = null

    FeignClient feignClient = null

    RequestMapping requestMapping = null

    ControllerAdvice controllerAdvice = null

    ExceptionHandler exceptionHandler = null

    ResponseStatus responseStatus = null

    // Method Level

    GetMapping getMapping = null

    PostMapping postMapping = null

    PutMapping putMapping = null

    DeleteMapping deleteMapping = null

    PatchMapping patchMapping = null

    // Field Level

    PathVariable pathVariable = null

    RequestParam requestParam = null

    RequestPart requestPart = null

    RequestHeader requestHeader = null

    CookieValue cookieValue = null

    RequestBody requestBody = null

    RequestAttribute requestAttribute = null

    ModelAttribute modelAttribute = null

    Validated validated = null

    static SPRING_ANNOTATIONS = [
            RestController,
            ControllerAdvice,
            ExceptionHandler,
            ResponseStatus,
            FeignClient,
            RequestMapping,

            GetMapping,
            PostMapping,
            PutMapping,
            DeleteMapping,
            PatchMapping,

            PathVariable,
            RequestParam,
            RequestPart,
            RequestHeader,
            RequestBody,
            RequestAttribute,
            ModelAttribute,
            Validated,
    ]

    static final String SPRING_PACKAGE = "org.springframework"

    static boolean hasSpringAnnotations(List<AnnotationExpr> annotationExprs, List<String> imports = []) {
        if (!imports.any {
            it.startsWith(SPRING_PACKAGE)
        }) {
            return false
        }

        def annotationNames = SPRING_ANNOTATIONS.collect {
            it.simpleName
        }

        return annotationExprs && annotationExprs.any {
            return annotationNames.contains(it.nameAsString)
        }
    }
}
