package com.evalon4j.annotation

import com.evalon4j.java.JavaComponent
import com.evalon4j.java.types.JavaReferenceType
import com.github.javaparser.ast.expr.*

class StringExtractor extends AbstractExtractor {
    StringExtractor() {

    }

    StringExtractor(JavaComponent javaComponent, List<JavaReferenceType> referenceTypes) {
        super(javaComponent, referenceTypes)
    }

    @Override
    def extract(Expression expr) {
        if (expr instanceof FieldAccessExpr) { // 查找静态变量
            return AnnotationExtractorHelper.extractStaticReferenceValue(expr, javaComponent, referenceTypes)
        }

        if (expr instanceof BinaryExpr) {
            return concatExpression(expr)
        }

        if (expr instanceof ClassExpr) {
            return expr.typeAsString
        }

        if (expr instanceof StringLiteralExpr) {
            return expr.value
        }

        return ""
    }

    String concatExpression(Expression expr) {
        String concat = ""

        if (expr.isBinaryExpr()) {
            expr = expr.asBinaryExpr()

            if (expr.operator != BinaryExpr.Operator.PLUS) { // 仅支持 + 号
                return concat
            }

            def left = expr.left

            def right = expr.right

            concat += concatExpression(left)

            concat += concatExpression(right)
        }

        if (expr.isNameExpr()) {
            concat += AnnotationExtractorHelper.extractStaticReferenceValue(expr.asNameExpr(), javaComponent, referenceTypes)
        }

        if (expr.isLiteralStringValueExpr()) {
            concat += expr.asLiteralStringValueExpr().value
        }

        return concat
    }
}
