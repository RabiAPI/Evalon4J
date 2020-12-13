package com.evalon4j.frameworks.swagger

import com.evalon4j.frameworks.Annotation

/**
 * Represents an external documentation description.
 *
 * @since 1.5.0
 */
class ExternalDocs extends Annotation {
    /**
     * A short description of the target documentation. GFM syntax can be used for rich text representation.
     */
    String value = "";

    /**
     * URL for the docs.
     */
    String url = "";
}
