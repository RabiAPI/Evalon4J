package com.evalon4j.frameworks.validation
/**
 * The annotated element must be a strictly positive number (i.e. 0 is considered as an
 * invalid value).
 * <p>
 * Supported types are:
 * <ul>
 *     <li>{@code BigDecimal}</li>
 *     <li>{@code BigInteger}</li>
 *     <li>{@code byte}, {@code short}, {@code int}, {@code long}, {@code float},
 * {@code double} and their respective wrappers</li>
 * </ul>
 * <p>
 * {@code null} elements are considered valid.
 *
 * @author Gunnar Morling
 * @since 2.0
 */
class Positive extends JavaValidationAnnotation {
    String annotationName = "@Positive"
}