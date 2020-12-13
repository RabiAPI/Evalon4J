package com.evalon4j.frameworks.jax_rs

import com.evalon4j.frameworks.Annotation

/**
 * Identifies the application path that serves as the base URI
 * for all resource URIs provided by {@link javax.ws.rs.Path}. May only be
 * applied to a subclass of {@link javax.ws.rs.core.Application}.
 *
 * <p>When published in a Servlet container, the value of the application path
 * may be overridden using a servlet-mapping element in the web.xml.</p>
 *
 * @author Paul Sandoz
 * @author Marc Hadley
 * @see javax.ws.rs.core.Application
 * @see Path
 * @since 1.1
 */
class ApplicationPath extends Annotation {
    /**
     * Defines the base URI for all resource URIs. A trailing '/' character will
     * be automatically appended if one is not present.
     *
     * <p>The supplied value is automatically percent
     * encoded to conform to the {@code path} production of
     * {@link <a href="http://tools.ietf.org/html/rfc3986#section-3.3">RFC 3986 section 3.3</a>}.
     * Note that percent encoded values are allowed in the value, an
     * implementation will recognize such values and will not double
     * encode the '%' character.</p>
     */
    String value = ""
}
