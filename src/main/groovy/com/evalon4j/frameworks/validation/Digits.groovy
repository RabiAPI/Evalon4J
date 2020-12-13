package com.evalon4j.frameworks.validation
/**
 * The annotated element must be a number within accepted range
 * Supported types are:
 * <ul>
 *     <li>{@code BigDecimal}</li>
 *     <li>{@code BigInteger}</li>
 *     <li>{@code CharSequence}</li>
 *     <li>{@code byte}, {@code short}, {@code int}, {@code long}, and their respective
 *     wrapper types</li>
 * </ul>
 * <p>
 * {@code null} elements are considered valid.
 *
 * @author Emmanuel Bernard
 */
class Digits extends JavaValidationAnnotation {
    String annotationName = "@Digits"

    /**
     * @return maximum number of integral digits accepted for this number
     */
    int integer = 0;

    /**
     * @return maximum number of fractional digits accepted for this number
     */
    int fraction = 0;
}
