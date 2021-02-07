package com.evalon4j.frameworks.swagger_annotations

import com.evalon4j.frameworks.Annotation

/**
 * An optionally named list of extension properties.
 *
 * @since 1.5.0
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
