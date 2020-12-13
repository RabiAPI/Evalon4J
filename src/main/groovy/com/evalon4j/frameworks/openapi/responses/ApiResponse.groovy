package com.evalon4j.frameworks.openapi.responses

import com.evalon4j.frameworks.Annotation
import com.evalon4j.frameworks.openapi.extensions.Extension
import com.evalon4j.frameworks.openapi.headers.Header
import com.evalon4j.frameworks.openapi.media.Content

/**
 * The annotation may be used at method level or as field of {@link io.swagger.v3.oas.annotations.Operation} to define one or more responses of the
 * Operation.
 *
 * <p>swagger-jaxrs2 reader engine considers this annotation along with method return type and context as input to
 * resolve the OpenAPI Operation responses.</p>
 *
 * @see <a target="_new" href="https://github.com/OAI/OpenAPI-Specification/blob/3.0.1/versions/3.0.1.md#responseObject">Response (OpenAPI specification)</a>
 * @see io.swagger.v3.oas.annotations.Operation
 **/
class ApiResponse extends Annotation {
    /**
     * A short description of the response.
     *
     * @return description of the response
     **/
    String description= ""

    /**
     * The HTTP response code, or 'default', for the supplied response. May only have 1 default entry.
     *
     * @return response code
     **/
    String responseCode= "200"

    /**
     * An array of response headers. Allows additional information to be included with response.
     *
     * @return array of headers
     **/
    Header[] headers= []

    /**
     * An array of operation links that can be followed from the response.
     *
     * @return array of links
     **/
//    Link[] links= [];

    /**
     * An array containing descriptions of potential response payloads, for different media types.
     *
     * @return array of content
     **/
    Content[] content= []

    /**
     * The list of optional extensions
     *
     * @return an optional array of extensions
     */
    Extension[] extensions= []

    /**
     * A reference to a response defined in components responses.
     *
     * @since 2.0.3
     * @return the reference
     **/
    String ref= "" // ignore
}
