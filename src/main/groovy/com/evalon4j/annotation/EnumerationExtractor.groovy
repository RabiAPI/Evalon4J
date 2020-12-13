package com.evalon4j.annotation

import com.github.javaparser.ast.expr.Expression
import com.github.javaparser.ast.expr.FieldAccessExpr

class EnumerationExtractor extends AbstractExtractor {
    Class enumeration

    EnumerationExtractor(Class enumeration) {
        this.enumeration = enumeration
    }

    @Override
    def extract(Expression expr) {
        if (expr instanceof FieldAccessExpr) {
            String name = expr.nameAsString

            if (enumeration[name]) {
                return enumeration[name]
            }
        }

        return null
    }
}
