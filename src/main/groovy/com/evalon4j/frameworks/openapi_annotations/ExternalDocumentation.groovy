package com.evalon4j.frameworks.openapi_annotations

import com.evalon4j.frameworks.Annotation
import com.evalon4j.frameworks.openapi.extensions.Extension

class ExternalDocumentation extends Annotation {
    /**
     * A short description of the target documentation.
     */
    String description

    /**
     * The list of optional extensions
     */
    Extension[] extensions

    /**
     * The URL for the target documentation.
     */
    String url
}
