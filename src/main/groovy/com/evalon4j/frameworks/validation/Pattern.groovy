package com.evalon4j.frameworks.validation
/**
 * The annotated {@code CharSequence} must match the specified regular expression.
 * The regular expression follows the Java regular expression conventions
 * see {@link java.util.regex.Pattern}.
 * <p>
 * Accepts {@code CharSequence}. {@code null} elements are considered valid.
 *
 * @author Emmanuel Bernard
 */
class Pattern extends JavaValidationAnnotation {
    String annotationName = "@Pattern"

    /**
     * @return the regular expression to match
     */
    String regexp = ""

    /**
     * @return array of {@code Flag}s considered when resolving the regular expression
     */
//    Flag[] flags() default { };

    /**
     * @return the payload associated to the constraint
     */
//    Class<? extends Payload>[] payload() default { };
}
