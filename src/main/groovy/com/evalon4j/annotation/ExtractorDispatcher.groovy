package com.evalon4j.annotation

import com.evalon4j.frameworks.Annotation
import com.evalon4j.java.JavaComponent
import com.evalon4j.java.types.JavaAbstractType
import com.evalon4j.java.types.JavaReferenceType

class ExtractorDispatcher {
    JavaComponent javaComponent

    List<JavaReferenceType> referenceTypes = []

    ExtractorDispatcher(JavaComponent javaComponent, List<JavaReferenceType> referenceTypes) {
        this.javaComponent = javaComponent

        this.referenceTypes = referenceTypes
    }

    static JAVA_NUMBER_TYPES = [
            char,
            Character,
            short,
            Short,
            int,
            Integer,
            long,
            Long,
            float,
            Float,
            double,
            Double,
    ]

    static JAVA_BOOLEAN_TYPES = [
            boolean,
            Boolean,
    ]

    AbstractExtractor dispatch(Class fieldType) {
        if (fieldType == JavaAbstractType) {
            return new JavaAbstractTypeExtractor(javaComponent, referenceTypes)
        }

        if (fieldType.isEnum()) {
            return new EnumerationExtractor(fieldType)
        }

        if (fieldType == String) {
            return new StringExtractor(javaComponent, referenceTypes)
        }

        if (JAVA_NUMBER_TYPES.contains(fieldType)) {
            return new NumberExtractor(javaComponent, referenceTypes)
        }

        if (JAVA_BOOLEAN_TYPES.contains(fieldType)) {
            return new BooleanExtractor(javaComponent, referenceTypes)
        }

        if (fieldType.isArray()) {
            return new ArrayExtractor(fieldType.componentType, javaComponent, referenceTypes)
        }

        if (fieldType.superclass == Annotation) { // fieldType 依然是一个注解类
            return new AnnotationExtractor(fieldType, javaComponent, referenceTypes)
        }

        return new DummyExtractor()
    }
}
