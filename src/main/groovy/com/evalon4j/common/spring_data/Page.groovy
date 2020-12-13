package com.evalon4j.common.spring_data

import com.evalon4j.common.Pojo
import com.evalon4j.java.types.JavaAbstractType
import com.evalon4j.java.types.JavaGenericType

/**
 * Page in Spring Data
 */
class Page<T> extends Pojo {
    List<T> content

    int numberOfElements

    int numberOfPages

    @Override
    JavaAbstractType build() {
        JavaGenericType genericType = new JavaGenericType()

        // TODO

        return genericType
    }
}
