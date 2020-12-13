package com.evalon4j.annotation

import com.evalon4j.java.JavaComponent
import com.evalon4j.java.types.JavaAbstractType
import com.evalon4j.java.types.JavaReferenceType
import com.evalon4j.visitors.JavaAbstractTypeBuilder
import com.github.javaparser.ast.expr.ClassExpr
import com.github.javaparser.ast.expr.Expression

class JavaAbstractTypeExtractor extends AbstractExtractor {
    JavaAbstractTypeExtractor() {
    }

    JavaAbstractTypeExtractor(JavaComponent javaComponent, List<JavaReferenceType> referenceTypes) {
        super(javaComponent, referenceTypes)
    }

    @Override
    JavaAbstractType extract(Expression expr) {
        def classExpr = (expr as ClassExpr)

        return new JavaAbstractTypeBuilder(
                javaComponent.moduleName,
                javaComponent.packageName,
                javaComponent.imports).buildFieldTypeRecursive(classExpr.type)
    }
}
