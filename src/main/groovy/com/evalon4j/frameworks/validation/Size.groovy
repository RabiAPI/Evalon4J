package com.evalon4j.frameworks.validation
/**
 * The annotated element size must be between the specified boundaries (included).
 * <p>
 * Supported types are:
 * <ul>
 *     <li>{@code CharSequence} (length of character sequence is evaluated)</li>
 *     <li>{@code Collection} (collection size is evaluated)</li>
 *     <li>{@code Map} (map size is evaluated)</li>
 *     <li>Array (array length is evaluated)</li>
 * </ul>
 * <p>
 * {@code null} elements are considered valid.
 *
 * @author Emmanuel Bernard
 */
class Size extends JavaValidationAnnotation {
    String annotationName = "@Size"

    /**
     * @return size the element must be higher or equal to
     */
    int min = 0;

    /**
     * @return size the element must be lower or equal to
     */
    int max = Integer.MAX_VALUE;
}
