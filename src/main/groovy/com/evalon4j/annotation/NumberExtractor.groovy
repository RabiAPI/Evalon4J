package com.evalon4j.annotation

import com.evalon4j.java.JavaComponent
import com.evalon4j.java.types.JavaReferenceType
import com.github.javaparser.ast.expr.*

class NumberExtractor extends AbstractExtractor {
    NumberExtractor() {
    }

    NumberExtractor(JavaComponent javaComponent, List<JavaReferenceType> referenceTypes) {
        super(javaComponent, referenceTypes)
    }

    @Override
    def extract(Expression expr) {
        if (expr instanceof FieldAccessExpr) {
            return AnnotationExtractorHelper.extractStaticReferenceValue(expr, javaComponent, referenceTypes)
        }

        if (expr instanceof IntegerLiteralExpr) {
            return expr.asNumber()
        }

        if (expr instanceof LongLiteralExpr) {
            return expr.asNumber()
        }

        if (expr instanceof DoubleLiteralExpr) {
            return expr.asDouble()
        }

        return 0
    }
}
