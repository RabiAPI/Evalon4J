package com.evalon4j.frameworks.jax_rs

import com.evalon4j.frameworks.Annotation

/**
 * Defines the default value of request meta-data that is bound using one of the
 * following annotations:
 * {@link javax.ws.rs.PathParam},
 * {@link javax.ws.rs.QueryParam},
 * {@link javax.ws.rs.MatrixParam},
 * {@link javax.ws.rs.CookieParam},
 * {@link javax.ws.rs.FormParam},
 * or {@link javax.ws.rs.HeaderParam}.
 * The default value is used if the corresponding meta-data is not present in the
 * request.
 * <p>
 * If the type of the annotated parameter is {@link java.util.List},
 * {@link java.util.Set} or {@link java.util.SortedSet} then the resulting collection
 * will have a single entry mapped from the supplied default value.
 * </p>
 * <p>
 * If this annotation is not used and the corresponding meta-data is not
 * present in the request, the value will be an empty collection for
 * {@code List}, {@code Set} or {@code SortedSet}, {@code null} for
 * other object types, and the Java-defined default for primitive types.
 * </p>
 *
 * @author Paul Sandoz
 * @author Marc Hadley
 * @see PathParam
 * @see QueryParam
 * @see FormParam
 * @see HeaderParam
 * @see MatrixParam
 * @see CookieParam
 * @since 1.0
 */
class DefaultValue extends Annotation {
    /**
     * The specified default value.
     */
    String value = ""
}
