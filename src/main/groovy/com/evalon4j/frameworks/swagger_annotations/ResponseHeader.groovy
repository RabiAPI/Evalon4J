package com.evalon4j.frameworks.swagger_annotations


import com.evalon4j.frameworks.Annotation

/**
 * Represents a header that can be provided as part of the response.
 */
class ResponseHeader extends Annotation {
    /**
     * Header's name.
     */
    String name = "";

    /**
     * Long description of the response header.
     */
    String description = "";
}
