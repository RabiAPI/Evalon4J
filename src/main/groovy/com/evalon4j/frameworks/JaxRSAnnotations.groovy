package com.evalon4j.frameworks

import com.github.javaparser.ast.expr.AnnotationExpr
import com.evalon4j.frameworks.jax_rs.*

class JaxRSAnnotations {
    ApplicationPath applicationPath = null

    Path path = null

    PathParam pathParam = null

    QueryParam queryParam = null

    CookieParam cookieParam = null

    FormParam formParam = null

    HeaderParam headerParam = null

    GET get = null

    POST post = null

    PUT put = null

    DELETE delete = null

    OPTIONS options = null

    PATCH patch = null

    Consumes consumes = null

    Produces produces = null

    DefaultValue defaultValue = null

    static JAX_RS_ANNOTATIONS = [
            ApplicationPath,
            Path,

            QueryParam,
            PathParam,
            CookieParam,
            FormParam,
            HeaderParam,
            MatrixParam,
            DefaultValue,

            GET,
            POST,
            PUT,
            DELETE,
            OPTIONS,
            PATCH,

            Consumes,
            Produces,
            DefaultValue,
    ]

    static final String JAX_RS_PACKAGE = "javax.ws.rs"

    static boolean hasJaxRSAnnotations(List<AnnotationExpr> annotationExprs, List<String> imports) {
        if (!imports.any {
            it.startsWith(JAX_RS_PACKAGE)
        }) {
            return false
        }

        def annotationNames = JAX_RS_ANNOTATIONS.collect {
            it.simpleName
        }

        return annotationExprs && annotationExprs.any {
            return annotationNames.contains(it.nameAsString)
        }
    }
}
