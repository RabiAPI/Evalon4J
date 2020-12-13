package com.evalon4j.frameworks.validation
/**
 * The string has to be a well-formed email address. Exact semantics of what makes up a valid
 * email address are left to Bean Validation providers. Accepts {@code CharSequence}.
 *
 * @author Emmanuel Bernard
 * @author Hardy Ferentschik
 *
 * @since 2.0
 */

class Email extends JavaValidationAnnotation {
    String annotationName = "@Email"

    /**
     * @return an additional regular expression the annotated element must match. The default
     * is any string ('.*')
     */
    String regexp = ".*"
}
