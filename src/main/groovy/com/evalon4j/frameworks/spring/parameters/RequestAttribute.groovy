package com.evalon4j.frameworks.spring.parameters

import com.evalon4j.frameworks.Annotation

/**
 * Annotation to bind a method parameter to a request attribute.
 *
 * <p>The main motivation is to provide convenient access to request attributes
 * from a controller method with an optional/required check and a cast to the
 * target method parameter type.
 *
 * @author Rossen Stoyanchev
 * @since 4.3* @see RequestMapping* @see SessionAttribute
 */
class RequestAttribute extends Annotation {
    /**
     * Alias for {@link #name}.
     */
    String value = ""

    /**
     * The name of the request attribute to bind to.
     * <p>The default name is inferred from the method parameter name.
     */
    String name = ""

    /**
     * Whether the request attribute is required.
     * <p>Defaults to {@code true}, leading to an exception being thrown if
     * the attribute is missing. Switch this to {@code false} if you prefer
     * a {@code null} or Java 8 {@code java.util.Optional} if the attribute
     * doesn't exist.
     */
    boolean required = true
}
