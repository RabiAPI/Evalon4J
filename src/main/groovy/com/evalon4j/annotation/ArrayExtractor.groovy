package com.evalon4j.annotation

import com.evalon4j.java.JavaComponent
import com.evalon4j.java.types.JavaReferenceType
import com.github.javaparser.ast.expr.ArrayInitializerExpr
import com.github.javaparser.ast.expr.Expression

class ArrayExtractor extends AbstractExtractor {
    Class actualType

    ArrayExtractor() {

    }

    ArrayExtractor(Class actualType, JavaComponent javaComponent, List<JavaReferenceType> referenceTypes) {
        this.actualType = actualType

        this.javaComponent = javaComponent

        this.referenceTypes = referenceTypes
    }

    @Override
    def extract(Expression expr) {
        def results = []

        if (expr.isArrayInitializerExpr()) {
            (expr as ArrayInitializerExpr).values.each { value ->
                results << new ExtractorDispatcher(javaComponent, referenceTypes).dispatch(actualType).extract(value)
            }
        } else {
            results << new ExtractorDispatcher(javaComponent, referenceTypes).dispatch(actualType).extract(expr)
        }

        return results.toArray()
    }
}
