package com.evalon4j.frameworks.swagger_annotations

import com.evalon4j.frameworks.Annotation

/**
 * A mediaType/value property within a Swagger example
 *
 * @see Example
 * @since 1.5.4
 */
class ExampleProperty extends Annotation {
    /**
     * The name of the property.
     *
     * @return the name of the property
     */
    String mediaType = "";

    /**
     * The value of the example.
     *
     * @return the value of the example
     */
    String value = "";
}
