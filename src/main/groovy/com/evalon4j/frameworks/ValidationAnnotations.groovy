package com.evalon4j.frameworks

import com.evalon4j.frameworks.validation.*
import com.github.javaparser.ast.expr.AnnotationExpr

class ValidationAnnotations {
    List<Annotation> annotations = []

    static final String VALIDATION_PACKAGE = "javax.validation"

    static final String HIBERNATE_PACKAGE = "org.hibernate.validator"

    static VALIDATION_ANNOTATIONS = [
            DecimalMin,
            DecimalMax,
            Min,
            Max,
            Pattern,
            Size,
            Length,
            Range,

            // Required

            NotNull,
            NotEmpty,
            NotBlank,

            // DateTime

            Past,
            PastOrPresent,
            Future,
            FutureOrPresent
    ]

    static boolean hasValidationAnnotations(List<AnnotationExpr> annotationExprs, List<String> imports = []) {
        if (!imports.any {
            it.startsWith(VALIDATION_PACKAGE) || it.startsWith(HIBERNATE_PACKAGE)
        }) {
            return false
        }

        def annotationNames = VALIDATION_ANNOTATIONS.collect {
            it.simpleName
        }

        return annotationExprs && annotationExprs.any {
            return annotationNames.contains(it.nameAsString)
        }
    }
}
