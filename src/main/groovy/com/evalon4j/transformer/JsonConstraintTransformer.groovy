package com.evalon4j.transformer

import com.evalon4j.frameworks.ValidationAnnotations
import com.evalon4j.frameworks.spring.Validated
import com.evalon4j.frameworks.validation.*
import com.evalon4j.json.JsonConstraint
import com.evalon4j.json.JsonStruct
import com.evalon4j.visitors.JavaVisitorHelper

class JsonConstraintTransformer {
    static List<JsonStruct> transform(List<JsonStruct> jsonStructs, Validated validated = null) {
        def filterJsonStructs = []

        def clonedJsonStructs = JavaVisitorHelper.deepCopyJsonStructs(jsonStructs)

        clonedJsonStructs.each { jsonStruct ->
            if (jsonStruct.springAnnotations && jsonStruct.springAnnotations.validated) {
                !validated && (validated = jsonStruct.springAnnotations.validated)
            }

            def constraints = filterConstraintsByValidated(jsonStruct.validationAnnotations, validated)

            jsonStruct.constraint = buildJsonConstraint(jsonStruct)

            if (jsonStruct.isPrimitiveType && constraints) {
                filterJsonStructs << jsonStruct
            }

            if (jsonStruct.isArrayType || jsonStruct.isSetType) {
                jsonStruct.children = transform(jsonStruct.children, validated)

                if (constraints || jsonStruct.children) {
                    filterJsonStructs << jsonStruct
                }
            }

            if (jsonStruct.isMapType) { // key 固定存在，如果 value 中不存在约束条件，就过滤掉
                jsonStruct.children = transform(jsonStruct.children, validated)

                def values = transform([jsonStruct.children[1]], validated)

                if (constraints || values) {
                    jsonStruct.children[1] = values.first()

                    filterJsonStructs << jsonStruct
                }
            }

            if (jsonStruct.isStructType) {
                jsonStruct.children = transform(jsonStruct.children, validated)

                if (constraints || jsonStruct.children) {
                    filterJsonStructs << jsonStruct
                }
            }
        }

        return filterJsonStructs
    }

    private static List<JavaValidationAnnotation> filterConstraintsByValidated(ValidationAnnotations validationAnnotations, Validated validated) {
        if (!validationAnnotations) {
            return null
        }

        if (!validated) {
            return validationAnnotations.annotations
        }

        if (!validated.value) { // 没有指定校验分组
            return validationAnnotations.annotations.findAll { constraint ->
                return !constraint.groups
            }
        } else {
            return validationAnnotations.annotations.findAll { constraint ->
                def result = constraint.groups.toList().intersect(validated.value.toList())

                return !result.isEmpty()
            }
        }
    }

    private static JsonConstraint buildJsonConstraint(JsonStruct jsonStruct) {
        def va = jsonStruct.validationAnnotations

        if (!va || !va.annotations) {
            return null
        }

        def constraint = new JsonConstraint()

        va.annotations.each { jsr ->
            // Required Validation

            if (jsr instanceof NotNull || jsr instanceof NotEmpty || jsr instanceof NotBlank) {
                constraint.required = true
            }

            // Numeric Validation

            if (jsr instanceof DecimalMax) {
                if (jsr.inclusive) {
                    constraint.maximum = jsr.value
                } else {
                    constraint.exclusiveMaximum = jsr.value
                }
            }

            if (jsr instanceof DecimalMin) {
                if (jsr.inclusive) {
                    constraint.minimum = jsr.value
                } else {
                    constraint.exclusiveMinimum = jsr.value
                }
            }

            if (jsr instanceof Max) {
                constraint.maximum = jsr.value
            }

            if (jsr instanceof Min) {
                constraint.minimum = jsr.value
            }

            if (jsr instanceof Pattern) {
                constraint.pattern = jsr.regexp
            }

            if (jsr instanceof Size) {
                if (jsonStruct.isArrayType || jsonStruct.isMapType) {
                    constraint.minItems = jsr.min
                    constraint.maxItems = jsr.max
                }

                if (jsonStruct.isStringType) {
                    constraint.minLength = jsr.min
                    constraint.maxLength = jsr.max
                }

                if (jsonStruct.isNumberType) {
                    constraint.minimum = jsr.min
                    constraint.maximum = jsr.max
                }
            }

            // number

            if (jsr instanceof Positive) {
                constraint.positive = true
            }

            if (jsr instanceof PositiveOrZero) {
                constraint.positiveOrZero = true
            }

            if (jsr instanceof Negative) {
                constraint.negative = true
            }

            if (jsr instanceof NegativeOrZero) {
                constraint.negativeOrZero = true
            }

            // date-time

            if (jsr instanceof Past) {
                constraint.past = true
            }

            if (jsr instanceof PastOrPresent) {
                constraint.pastOrPresent = true
            }

            if (jsr instanceof Future) {
                constraint.future = true
            }

            if (jsr instanceof FutureOrPresent) {
                constraint.futureOrPresent = true
            }
        }

        return constraint
    }
}
