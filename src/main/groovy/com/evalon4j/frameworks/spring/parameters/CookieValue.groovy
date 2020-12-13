package com.evalon4j.frameworks.spring.parameters

import com.evalon4j.frameworks.Annotation

/**
 * Annotation which indicates that a method parameter should be bound to an HTTP cookie.
 *
 * <p>The method parameter may be declared as type {@link javax.servlet.http.Cookie}
 * or as cookie value type (String, int, etc.).
 *
 * @author Juergen Hoeller
 * @author Sam Brannen
 * @since 3.0* @see RequestMapping* @see RequestParam* @see RequestHeader* @see org.springframework.web.bind.annotation.RequestMapping
 */
class CookieValue extends Annotation {
    /**
     * Alias for {@link #name}.
     */
    String value = ""

    /**
     * The name of the cookie to bind to.
     * @since 4.2
     */
    String name = ""

    /**
     * Whether the cookie is required.
     * <p>Defaults to {@code true}, leading to an exception being thrown
     * if the cookie is missing in the request. Switch this to
     * {@code false} if you prefer a {@code null} value if the cookie is
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
