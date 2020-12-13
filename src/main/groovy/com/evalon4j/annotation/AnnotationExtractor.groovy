package com.evalon4j.annotation

import com.evalon4j.frameworks.Annotation
import com.evalon4j.java.JavaComponent
import com.evalon4j.java.types.JavaReferenceType
import com.github.javaparser.ast.expr.Expression
import com.github.javaparser.ast.expr.NormalAnnotationExpr
import com.github.javaparser.ast.expr.SingleMemberAnnotationExpr

class AnnotationExtractor extends AbstractExtractor {
    Class annotationType

    AnnotationExtractor(Class annotationType) {
        super()

        this.annotationType = annotationType
    }

    AnnotationExtractor(Class annotationType, JavaComponent javaComponent, List<JavaReferenceType> referenceTypes) {
        this.annotationType = annotationType

        this.javaComponent = javaComponent

        this.referenceTypes = referenceTypes
    }

    @Override
    Annotation extract(Expression expr) {
        def annotation = annotationType.newInstance()

        expr.isNormalAnnotationExpr() && (expr as NormalAnnotationExpr).pairs.each { pair ->
            String memberName = pair.nameAsString

            def field = null

            def childField = annotationType.declaredFields.find { f ->
                return f.name.replaceAll("_", "") == memberName
            }

            def parentField = annotationType.superclass.declaredFields.find { f ->
                return f.name.replaceAll("_", "") == memberName // 替换用于关键字的下划线
            }

            childField && (field = childField)

            parentField && (field = parentField)

            if (field) {
                def extractor = new ExtractorDispatcher(javaComponent, referenceTypes).dispatch(field.type)

                annotation.setProperty(field.name, extractor.extract(pair.value))

                return
            }
        }

        if (expr.isSingleMemberAnnotationExpr()) {
            def valueField = null

            def childField = annotationType.declaredFields.find { field ->
                return field.name == "value"
            }

            def parentField = annotationType.superclass.declaredFields.find { field ->
                return field.name == "value"
            }

            childField && (valueField = childField)

            parentField && (valueField = parentField)

            if (valueField) {
                def memberAnnotationExpr = (expr as SingleMemberAnnotationExpr).memberValue

                def extractor = new ExtractorDispatcher(javaComponent, referenceTypes).dispatch(valueField.type)

                annotation.setProperty("value", extractor.extract(memberAnnotationExpr))
            }
        }

        return annotation
    }
}
