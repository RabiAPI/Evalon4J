package com.evalon4j.annotation

import com.evalon4j.frameworks.spring.HttpStatus
import com.evalon4j.frameworks.spring.MediaType
import com.evalon4j.java.JavaComponent
import com.evalon4j.java.types.JavaReferenceType
import com.github.javaparser.ast.expr.FieldAccessExpr
import com.github.javaparser.ast.expr.NameExpr

class AnnotationExtractorHelper {
    static extractStaticReferenceValue(NameExpr expr, JavaComponent javaComponent, List<JavaReferenceType> referenceTypes) {
        String fieldName = expr.nameAsString

        def targetReferenceTypes = referenceTypes.findAll {
            it.qualifiedName == javaComponent.qualifiedName
        }

        def targetReferenceType

        // 多模块中出现同名类型时，优选使用本模块中的类型

        if (targetReferenceTypes.size() > 1) {
            targetReferenceType = targetReferenceTypes.find {
                it.moduleName == javaComponent.moduleName
            }

            !targetReferenceType && (targetReferenceType = targetReferenceTypes[0])
        } else {
            targetReferenceType = targetReferenceTypes[0]
        }

        if (!targetReferenceType) {
            return fieldName
        }

        def targetField = targetReferenceType.staticFields.find {
            it.fieldName == fieldName
        }

        if (!targetField) {
            return fieldName
        }

        return targetField.fieldValue
    }

    static STATIC_REFERENCES = [ // TODO spring / jax / swagger / openapi
            MediaType,
    ]

    static extractStaticReferenceValue(FieldAccessExpr expr, JavaComponent javaComponent, List<JavaReferenceType> referenceTypes) {
        String className = expr.scope

        String fieldName = expr.nameAsString

        def possibleQualifiedNames = [className] // scope 本身也可以是一个完整的引用名称

        javaComponent.imports.each { im ->
            if (im.endsWith("." + className)) {
                possibleQualifiedNames << im
            }

            if (im.endsWith("*")) {
                possibleQualifiedNames << im.replace("*", className)
            }
        }

        def possibleReferenceTypes = referenceTypes.findAll { referenceType ->
            return possibleQualifiedNames.contains(referenceType.qualifiedName)
        }

        if (!possibleReferenceTypes) {
            def reference = STATIC_REFERENCES.find {
                it.simpleName == className
            }

            if (reference[fieldName]) {
                return reference[fieldName]
            }

            return expr.toString()
        }

        def targetReferenceType = possibleReferenceTypes.find { referenceType ->
            referenceType.staticFields.any {
                it.fieldName == fieldName
            }
        }

        if (!targetReferenceType) {
            return expr.toString()
        }

        def targetField = targetReferenceType.staticFields.find {
            it.fieldName == fieldName
        }

        return targetField.fieldValue
    }
}
