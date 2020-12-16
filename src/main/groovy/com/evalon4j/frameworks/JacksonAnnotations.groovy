package com.evalon4j.frameworks

import com.evalon4j.frameworks.jackson.JsonIgnore
import com.evalon4j.frameworks.jackson.JsonIgnoreProperties
import com.github.javaparser.ast.expr.AnnotationExpr

class JacksonAnnotations {
    JsonIgnore jsonIgnore = null

    JsonIgnoreProperties jsonIgnoreProperties = null

    static JACKSON_ANNOTATIONS = [
            JsonIgnore,
            JsonIgnoreProperties,
    ]

    static final String JACKSON_PACKAGE = "com.fasterxml.jackson"

    static boolean hasJacksonAnnotations(List<AnnotationExpr> annotationExprs, List<String> imports) {
        if (!imports.any {
            it.startsWith(JACKSON_PACKAGE)
        }) {
            return false
        }

        def annotationNames = JACKSON_ANNOTATIONS.collect {
            it.simpleName
        }

        return annotationExprs && annotationExprs.any {
            return annotationNames.contains(it.nameAsString)
        }
    }
}
