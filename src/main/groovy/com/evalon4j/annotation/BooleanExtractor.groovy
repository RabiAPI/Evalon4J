package com.evalon4j.annotation

import com.evalon4j.java.JavaComponent
import com.evalon4j.java.types.JavaReferenceType
import com.github.javaparser.ast.expr.BooleanLiteralExpr
import com.github.javaparser.ast.expr.Expression
import com.github.javaparser.ast.expr.FieldAccessExpr

class BooleanExtractor extends AbstractExtractor {
    BooleanExtractor() {
    }

    BooleanExtractor(JavaComponent javaComponent, List<JavaReferenceType> referenceTypes) {
        super(javaComponent, referenceTypes)
    }

    def extract(Expression expr) {
        if (expr instanceof FieldAccessExpr) {
            return AnnotationExtractorHelper.extractStaticReferenceValue(expr, javaComponent, referenceTypes)
        }

        if (expr instanceof BooleanLiteralExpr) {
            return expr.value
        }

        return false
    }
}
