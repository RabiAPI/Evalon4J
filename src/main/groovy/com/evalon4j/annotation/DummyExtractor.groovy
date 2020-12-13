package com.evalon4j.annotation


import com.github.javaparser.ast.expr.Expression

class DummyExtractor extends AbstractExtractor {
    @Override
    def extract(Expression expr) {
        return null
    }
}
