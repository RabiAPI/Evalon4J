package com.evalon4j.annotation

import com.evalon4j.java.JavaComponent
import com.evalon4j.java.types.JavaReferenceType
import com.github.javaparser.ast.expr.Expression

abstract class AbstractExtractor {
    JavaComponent javaComponent = null

    List<JavaReferenceType> referenceTypes = []

    AbstractExtractor() {

    }

    AbstractExtractor(JavaComponent javaComponent, List<JavaReferenceType> referenceTypes) {
        this.javaComponent = javaComponent

        this.referenceTypes = referenceTypes
    }

    abstract def extract(Expression expr)
}