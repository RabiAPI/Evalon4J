package com.evalon4j.frameworks.swagger_annotations

import com.evalon4j.frameworks.Annotation

/**
 * Annotation used to construct Basic Auth security definition.
 */
class BasicAuthDefinition extends Annotation {
    /**
     * Key used to refer to this security definition
     *
     * @return key used to refer to this security definition
     */
    String key = "";

    /**
     * A short description for security scheme.
     *
     * @return a short description for security scheme.
     */
    String description = "";
}
