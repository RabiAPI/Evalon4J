package com.evalon4j.frameworks.swagger_annotations

import com.alibaba.fastjson.annotation.JSONField
import com.evalon4j.frameworks.Annotation
import com.evalon4j.java.types.JavaAbstractType

/**
 * Represents a single parameter in an API Operation.
 * <p>
 * While {@link ApiParam} is bound to a JAX-RS parameter,
 * method or field, this allows you to manually define a parameter in a fine-tuned manner.
 * This is the only way to define parameters when using Servlets or other non-JAX-RS
 * environments.
 * <p>
 * This annotation must be used as a value of {@link ApiImplicitParams}
 * in order to be parsed.
 *
 * @see ApiImplicitParams
 */
class ApiImplicitParam extends Annotation {
    /**
     * Name of the parameter.
     * <p>
     * For proper Swagger functionality, follow these rules when naming your parameters based on {@link #paramType()}:
     * <ol>
     * <li>If {@code paramType} is "path", the name should be the associated section in the path.</li>
     * <li>For all other cases, the name should be the parameter name as your application expects to accept.</li>
     * </ol>
     *
     * @see #paramType()
     */
    String name = ""

    /**
     * A brief description of the parameter.
     */
    String value = ""

    /**
     * Describes the default value for the parameter.
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
     * Path parameters should always be set as required.
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
     * The data type of the parameter.
     * <p>
     * This can be the class name or a primitive.
     */
    String dataType = ""

    /**
     * The class of the parameter.
     * <p>
     * Overrides {@code dataType} if provided.
     */
    @JSONField(serialize = false)
    JavaAbstractType dataTypeClass = null

    /**
     * The parameter type of the parameter.
     * <p>
     * Valid values are {@code path}, {@code query}, {@code body},
     * {@code header} or {@code form}.
     */
    String paramType = "query"

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
    Boolean allowEmptyValue = false

    /**
     * adds ability to be designated as read only.
     *
     * @since 1.5.11*
     */
    Boolean readOnly = false

    /**
     * adds ability to override collectionFormat with `array` types
     *
     * @since 1.5.11*
     */
    String collectionFormat = ""
}
