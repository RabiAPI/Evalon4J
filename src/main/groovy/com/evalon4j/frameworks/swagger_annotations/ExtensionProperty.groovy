package com.evalon4j.frameworks.swagger_annotations

import com.evalon4j.frameworks.Annotation

/**
 * A name/value property within a Swagger extension
 *
 * @see Extension
 * @since 1.5.0
 */
class ExtensionProperty extends Annotation {
    /**
     * The name of the property.
     *
     * @return the name of the property
     */
    String name = "";

    /**
     * The value of the property.
     *
     * @return the value of the property
     */
    String value = "";

    /**
     * If set to true, field `value` will be parsed and serialized as JSON/YAML
     *
     * @return the value of `parseValue` annotation field
     */
    boolean parseValue = false;
}
