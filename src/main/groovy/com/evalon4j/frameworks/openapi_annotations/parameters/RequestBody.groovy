package com.evalon4j.frameworks.openapi_annotations.parameters

import com.evalon4j.frameworks.Annotation
import com.evalon4j.frameworks.openapi_annotations.extensions.Extension
import com.evalon4j.frameworks.openapi_annotations.media.Content

/**
 * The annotation may be used on a method parameter to define it as the Request Body of the operation, and/or to define
 * additional properties for such request body.
 *
 * */
class RequestBody extends Annotation {
    /**
     * A brief description of the request body.
     *
     * @return description of the request body
     * */
    String description = "";

    /**
     * The content of the request body.
     *
     * @return array of content
     * */
    Content[] content = [];

    /**
     * Determines if the request body is required in the request. Defaults to false.
     *
     * @return boolean*  */
    boolean required = false;

    /**
     * The list of optional extensions
     *
     * @return an optional array of extensions
     */
    Extension[] extensions = [];

    /**
     * A reference to a RequestBody defined in components RequestBodies.
     *
     * @since 2.0.3* @return the reference
     * */
    String ref = "";
}
