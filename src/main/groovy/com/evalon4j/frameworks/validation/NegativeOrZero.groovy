package com.evalon4j.frameworks.validation
/**
 * The annotated element must be a negative number or 0.
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

class NegativeOrZero extends JavaValidationAnnotation {
    String annotationName = "@NegativeOrZero"
}
