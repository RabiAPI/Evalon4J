package com.evalon4j.frameworks.spring.parameters

import com.evalon4j.frameworks.Annotation

/**
 * Annotation which indicates that a method parameter should be bound to a web request header.
 *
 * <p>Supported for annotated handler methods in Spring MVC and Spring WebFlux.
 *
 * <p>If the method parameter is {@link java.util.Map Map&lt;String, String&gt;},
 * {@link org.springframework.util.MultiValueMap MultiValueMap&lt;String, String&gt;},
 * or {@link org.springframework.http.HttpHeaders HttpHeaders} then the map is
 * populated with all header names and values.
 *
 * @author Juergen Hoeller
 * @author Sam Brannen
 * @since 3.0* @see RequestMapping* @see RequestParam* @see CookieValue
 */
class RequestHeader extends Annotation {
    /**
     * Alias for {@link #name}.
     */
    String value = ""

    /**
     * The name of the request header to bind to.
     * @since 4.2
     */
    String name = ""

    /**
     * Whether the header is required.
     * <p>Defaults to {@code true}, leading to an exception being thrown
     * if the header is missing in the request. Switch this to
     * {@code false} if you prefer a {@code null} value if the header is
     * not present in the request.
     * <p>Alternatively, provide a {@link #defaultValue}, which implicitly
     * sets this flag to {@code false}.
     */
    boolean required = true

    /**
     * The default value to use as a fallback.
     * <p>Supplying a default value implicitly sets {@link #required} to
     * {@code false}.
     */
    String defaultValue = null
}
