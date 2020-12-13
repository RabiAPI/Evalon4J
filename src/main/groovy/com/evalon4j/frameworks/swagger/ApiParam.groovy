package com.evalon4j.frameworks.swagger

import com.evalon4j.frameworks.Annotation

/**
 * Adds additional meta-data for operation parameters.
 * <p>
 * This annotation can be used only in combination of JAX-RS 1.x/2.x annotations.
 */
class ApiParam extends Annotation {
    /**
     * The parameter name.
     * <p>
     * The name of the parameter will be derived from the field/method/parameter name,
     * however you can override it.
     * <p>
     * Path parameters must always be named as the path section they represent.
     */
    String name = ""

    /**
     * A brief description of the parameter.
     */
    String value = ""

    /**
     * Describes the default value for the parameter.
     * <p>
     * If the parameter is annotated with JAX-RS's {@code @DefaultValue}, that value would
     * be used, but can be overridden by setting this property.
     */
    String defaultValue = ""

    /**
     * Limits the acceptable values for this parameter.
     * <p>
     * There are three ways to describe the allowable values:
     * <ol>
     * <li>To set a list of values, provide a comma-separated list.
     * For example: {@code first, second, third}.</li>
     * <li>To set a range of values, start the value with "range", and surrounding by square
     * brackets include the minimum and maximum values, or round brackets for exclusive minimum and maximum values.
     * For example: {@code range[1, 5]}, {@code range(1,5)}, {@code range[1, 5)}.</li>
     * <li>To set a minimum/maximum value, use the same format for range but use "infinity"
     * or "-infinity" as the second value. For example, {@code range[1, infinity]} means the
     * minimum allowable value of this parameter is 1.</li>
     * </ol>
     */
    String allowableValues = ""

    /**
     * Specifies if the parameter is required or not.
     * <p>
     * Path parameters will always be set as required, whether you set this property or not.
     */
    boolean required = false

    /**
     * Allows for filtering a parameter from the API documentation.
     * <p>
     * See io.swagger.core.filter.SwaggerSpecFilter for further details.
     */
    String access = ""

    /**
     * Specifies whether the parameter can accept multiple values by having multiple occurrences.
     */
    boolean allowMultiple = false

    /**
     * Hides the parameter from the list of parameters.
     */
    boolean hidden = false

    /**
     * a single example for non-body type parameters
     *
     * @since 1.5.4*
     * @return
     */
    String example = ""

    /**
     * Examples for the parameter.  Applies only to BodyParameters
     *
     * @since 1.5.4*
     * @return
     */
    Example examples = null


    /**
     * Adds the ability to override the detected type
     *
     * @since 1.5.11*
     * @return
     */
    String type = ""

    /**
     * Adds the ability to provide a custom format
     *
     * @since 1.5.11*
     * @return
     */
    String format = ""

    /**
     * Adds the ability to set a format as empty
     *
     * @since 1.5.11*
     * @return
     */
    boolean allowEmptyValue = false

    /**
     * adds ability to be designated as read only.
     *
     * @since 1.5.11*
     */
    boolean readOnly = false

    /**
     * adds ability to override collectionFormat with `array` types
     *
     * @since 1.5.11*
     */
    String collectionFormat = ""
}
