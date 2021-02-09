package com.evalon4j.visitors

import com.evalon4j.annotation.AnnotationExtractor
import com.evalon4j.frameworks.*
import com.evalon4j.frameworks.openapi_annotations.Parameter
import com.evalon4j.frameworks.swagger_annotations.ApiImplicitParam
import com.evalon4j.frameworks.swagger_annotations.ApiResponse
import com.evalon4j.java.JavaComponent
import com.evalon4j.java.types.JavaReferenceType
import com.github.javaparser.ast.expr.AnnotationExpr

class JavaAnnotationsBuilder {
    JavaComponent javaComponent

    List<JavaReferenceType> referenceTypes

    JavaAnnotationsBuilder() {

    }

    JavaAnnotationsBuilder(JavaComponent javaComponent, List<JavaReferenceType> referenceTypes = []) {
        this.javaComponent = javaComponent

        this.referenceTypes = referenceTypes
    }

    SpringAnnotations buildSpringAnnotations(List<AnnotationExpr> annotationExprs) {
        if (!SpringAnnotations.hasSpringAnnotations(annotationExprs, javaComponent.imports)) {
            return null
        }

        def springAnnotations = new SpringAnnotations()

        SpringAnnotations.SPRING_ANNOTATIONS.each { springAnnotation ->
            def annotationExpr = annotationExprs.find { annotationExpr ->
                annotationExpr.nameAsString == springAnnotation.simpleName
            }

            annotationExpr && springAnnotations.setProperty(annotationExpr.nameAsString.uncapitalize(),
                    new AnnotationExtractor(springAnnotation, javaComponent, referenceTypes).extract(annotationExpr))
        }

        return springAnnotations
    }

    JaxRSAnnotations buildJaxRSAnnotations(List<AnnotationExpr> annotationExprs) {
        if (!JaxRSAnnotations.hasJaxRSAnnotations(annotationExprs, javaComponent.imports)) {
            return null
        }

        def jaxRSAnnotations = new JaxRSAnnotations()

        JaxRSAnnotations.JAX_RS_ANNOTATIONS.each { jaxRSAnnotation ->
            def annotationExpr = annotationExprs.find { annotationExpr ->
                annotationExpr.nameAsString == jaxRSAnnotation.simpleName
            }

            if (!annotationExpr) {
                return
            }

            String annotationName = annotationExpr.nameAsString

            if (annotationName.toUpperCase() == annotationName) {
                annotationName = annotationName.toLowerCase()
            } else {
                annotationName = annotationName.uncapitalize()
            }

            jaxRSAnnotations.setProperty(annotationName, new AnnotationExtractor(jaxRSAnnotation, javaComponent, referenceTypes).extract(annotationExpr))
        }

        return jaxRSAnnotations
    }

    SwaggerAnnotations buildSwaggerAnnotations(List<AnnotationExpr> annotationExprs) {
        if (!SwaggerAnnotations.hasSwaggerAnnotation(annotationExprs, javaComponent.imports)) {
            return null
        }

        def swaggerAnnotations = new SwaggerAnnotations()

        SwaggerAnnotations.SWAGGER_ANNOTATIONS.each { swaggerAnnotation ->
            def annotationExpr = annotationExprs.find { annotationExpr ->
                annotationExpr.nameAsString == swaggerAnnotation.simpleName
            }

            if (!annotationExpr) {
                return
            }

            def annotation = new AnnotationExtractor(swaggerAnnotation, javaComponent, referenceTypes).extract(annotationExpr)

            if (annotation instanceof ApiImplicitParam) {
                swaggerAnnotations.apiImplicitParam << annotation

                return
            }

            if (annotation instanceof ApiResponse) {
                swaggerAnnotations.apiResponse << annotation

                return
            }

            swaggerAnnotations.setProperty(annotationExpr.nameAsString.uncapitalize(), annotation)
        }

        return swaggerAnnotations
    }

    OpenAPIAnnotations buildOpenAPIAnnotations(List<AnnotationExpr> annotationExprs) {
        if (!OpenAPIAnnotations.hasOpenAPIAnnotations(annotationExprs, javaComponent.imports)) {
            return null
        }

        def openapiAnnotations = new OpenAPIAnnotations()

        OpenAPIAnnotations.OPENAPI_ANNOTATIONS.each { openapiAnnotation ->
            def annotationExpr = annotationExprs.find { annotationExpr ->
                annotationExpr.nameAsString == openapiAnnotation.simpleName
            }

            if (!annotationExpr) {
                return
            }

            def annotation = new AnnotationExtractor(openapiAnnotation, javaComponent, referenceTypes).extract(annotationExpr)

            if (annotation instanceof Parameter) {
                openapiAnnotations.parameter << annotation

                return
            }

            if (annotation instanceof com.evalon4j.frameworks.openapi_annotations.responses.ApiResponse) {
                openapiAnnotations.apiResponse << annotation

                return
            }

            openapiAnnotations.setProperty(annotationExpr.nameAsString.uncapitalize(), new AnnotationExtractor(openapiAnnotation, javaComponent, referenceTypes).extract(annotationExpr))
        }

        return openapiAnnotations
    }

    ValidationAnnotations buildValidationAnnotations(List<AnnotationExpr> annotationExprs) {
        if (!ValidationAnnotations.hasValidationAnnotations(annotationExprs, javaComponent.imports)) {
            return null
        }

        def validationAnnotations = new ValidationAnnotations()

        ValidationAnnotations.VALIDATION_ANNOTATIONS.each { validationAnnotation ->
            def annotationExpr = annotationExprs.find { annotationExpr ->
                annotationExpr.nameAsString == validationAnnotation.simpleName
            }

            annotationExpr &&
                    validationAnnotations.annotations << new AnnotationExtractor(
                    validationAnnotation,
                    javaComponent,
                    referenceTypes).extract(annotationExpr)
        }

        return validationAnnotations
    }

    JacksonAnnotations buildJacksonAnnotations(List<AnnotationExpr> annotationExprs) {
        if (!JacksonAnnotations.hasJacksonAnnotations(annotationExprs, javaComponent.imports)) {
            return null
        }

        def jacksonAnnotations = new JacksonAnnotations()

        JacksonAnnotations.JACKSON_ANNOTATIONS.each { swaggerAnnotation ->
            def annotationExpr = annotationExprs.find { annotationExpr ->
                annotationExpr.nameAsString == swaggerAnnotation.simpleName
            }

            if (!annotationExpr) {
                return
            }

            def annotation = new AnnotationExtractor(swaggerAnnotation, javaComponent, referenceTypes).extract(annotationExpr)

            jacksonAnnotations.setProperty(annotationExpr.nameAsString.uncapitalize(), annotation)
        }

        return jacksonAnnotations
    }
}
