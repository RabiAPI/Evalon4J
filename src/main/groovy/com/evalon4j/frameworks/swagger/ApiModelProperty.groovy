package com.evalon4j.frameworks.swagger


import com.evalon4j.frameworks.Annotation

/**
 * Adds and manipulates data of a model property.
 */
class ApiModelProperty extends Annotation {
    /**
     * A brief description of this property.
     */
    String value = ""

    /**
     * Allows overriding the name of the property.
     *
     * @return the overridden property name
     */
    String name = ""

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
     * Allows for filtering a property from the API documentation. See io.swagger.core.filter.SwaggerSpecFilter.
     */
    String access = ""

    /**
     * Currently not in use.
     */
    String notes = ""

    /**
     * The data type of the parameter.
     * <p>
     * This can be the class name or a primitive. The value will override the data type as read from the class
     * property.
     */
    String dataType = ""

    /**
     * Specifies if the parameter is required or not.
     */
    boolean required = false

    /**
     * Allows explicitly ordering the property in the model.
     */
    int position = 0

    /**
     * Allows a model property to be hidden in the Swagger model definition.
     */
    boolean hidden = false

    /**
     * A sample value for the property.
     */
    String example = ""

    /**
     * Allows a model property to be designated as read only.
     *
     * @deprecated As of 1.5.19, replaced by {@link #accessMode()}
     *
     */
    @Deprecated
    boolean readOnly = false

    /**
     * Allows to specify the access mode of a model property (AccessMode.READ_ONLY, READ_WRITE)
     *
     * @since 1.5.19
     */
    AccessMode accessMode = AccessMode.AUTO


    /**
     * Specifies a reference to the corresponding type definition, overrides any other metadata specified
     */

    String reference = ""

    /**
     * Allows passing an empty value
     *
     * @since 1.5.11
     */
    boolean allowEmptyValue = false

    /**
     * @return an optional array of extensions
     */
    Extension[] extensions = []

    enum AccessMode {
        AUTO,
        READ_ONLY,
        READ_WRITE;
    }
}
