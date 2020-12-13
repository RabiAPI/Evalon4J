package com.evalon4j.frameworks.openapi.extensions

import com.evalon4j.frameworks.Annotation

/**
 * An optionally named list of extension properties.
 *
 * @see <a target="_new" href="https://github.com/OAI/OpenAPI-Specification/blob/3.0.1/versions/3.0.1.md#specificationExtensions">Specification extensions (OpenAPI specification)</a>
 */
class Extension extends Annotation {
    /**
     * An option name for these extensions.
     *
     * @return an option name for these extensions - will be prefixed with "x-"
     */
    String name = "";

    /**
     * The extension properties.
     *
     * @return the actual extension properties
     * @see ExtensionProperty
     */
    ExtensionProperty[] properties = [];
}
