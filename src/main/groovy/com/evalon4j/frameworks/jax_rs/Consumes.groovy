package com.evalon4j.frameworks.jax_rs

import com.evalon4j.frameworks.Annotation

/**
 * Defines the media types that the methods of a resource class or
 * {@link javax.ws.rs.ext.MessageBodyReader} can accept. If
 * not specified, a container will assume that any media type is acceptable.
 * Method level annotations override a class level annotation. A container
 * is responsible for ensuring that the method invoked is capable of consuming
 * the media type of the HTTP request entity body. If no such method is
 * available the container must respond with a HTTP "415 Unsupported Media Type"
 * as specified by RFC 2616.
 *
 * @author Paul Sandoz
 * @author Marc Hadley
 * @see javax.ws.rs.ext.MessageBodyReader
 * @since 1.0
 */
class Consumes extends Annotation {
    /**
     * A list of media types. Each entry may specify a single type or consist
     * of a comma separated list of types, with any leading or trailing white-spaces
     * in a single type entry being ignored. For example:
     * <pre>
     *  {"image/jpeg, image/gif ", " image/png"}
     * </pre>
     * Use of the comma-separated form allows definition of a common string constant
     * for use on multiple targets.
     */
    List<String> value = []
}
