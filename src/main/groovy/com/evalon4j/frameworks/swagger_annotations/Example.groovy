package com.evalon4j.frameworks.swagger_annotations

import com.evalon4j.frameworks.Annotation

/**
 * An optionally named list of example properties.
 *
 * @since 1.5.4
 */
class Example extends Annotation {
    /**
     * The examples properties.
     *
     * @return the actual extension properties
     * @see ExampleProperty
     */
    ExampleProperty[] value = [];
}
