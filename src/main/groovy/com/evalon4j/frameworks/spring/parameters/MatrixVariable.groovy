package com.evalon4j.frameworks.spring.parameters

import com.evalon4j.frameworks.Annotation

/**
 * Annotation which indicates that a method parameter should be bound to a
 * name-value pair within a path segment. Supported for {@link RequestMapping}
 * annotated handler methods.
 *
 * <p>If the method parameter type is {@link java.util.Map} and a matrix variable
 * name is specified, then the matrix variable value is converted to a
 * {@link java.util.Map} assuming an appropriate conversion strategy is available.
 *
 * <p>If the method parameter is {@link java.util.Map Map&lt;String, String&gt;} or
 * {@link org.springframework.util.MultiValueMap MultiValueMap&lt;String, String&gt;}
 * and a variable name is not specified, then the map is populated with all
 * matrix variable names and values.
 *
 * @author Rossen Stoyanchev
 * @author Sam Brannen
 * @since 3.2
 */
class MatrixVariable extends Annotation {
    /**
     * Alias for {@link #name}.
     */
    String value = ""

    /**
     * The name of the matrix variable.
     * @since 4.2* @see #value
     */
    String name = ""

    /**
     * The name of the URI path variable where the matrix variable is located,
     * if necessary for disambiguation (e.g. a matrix variable with the same
     * name present in more than one path segment).
     */
    String pathVar = null

    /**
     * Whether the matrix variable is required.
     * <p>Default is {@code true}, leading to an exception being thrown in
     * case the variable is missing in the request. Switch this to {@code false}
     * if you prefer a {@code null} if the variable is missing.
     * <p>Alternatively, provide a {@link #defaultValue}, which implicitly sets
     * this flag to {@code false}.
     */
    boolean required = true

    /**
     * The default value to use as a fallback.
     * <p>Supplying a default value implicitly sets {@link #required} to
     * {@code false}.
     */
    String defaultValue = null
}
