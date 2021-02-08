package com.evalon4j.frameworks.swagger

/**
 * comment
 *
 * @author whitecosm0s_
 */
class Header {
    /**
     * A brief description of the parameter. This could contain examples of use. GFM syntax can be used for rich text representation.
     */
    String description

    /**
     * Determines whether this parameter is mandatory.
     * If the parameter is in "path", this property is required and its value MUST be true.
     * Otherwise, the property MAY be included and its default value is false.
     */
    boolean required

    // If in is "body"

    /**
     * Required. The schema defining the type used for the body parameter.
     */
    Schema schema

    // If in is any value other than "body":

    /**
     * Required. The type of the parameter.
     * Since the parameter is not located at the request body, it is limited to simple types (that is, not an object).
     * The value MUST be one of "string", "number", "integer", "boolean", "array" or "file".
     * If type is "file", the consumes MUST be either "multipart/form-data", " application/x-www-form-urlencoded" or both and the parameter MUST be in "formData".
     */
    Object type

    /**
     * The extending format for the previously mentioned type. See Data Type Formats for further details.
     */
    Object format

    /**
     * Sets the ability to pass empty-valued parameters.
     * This is valid only for either query or formData parameters and allows you to send a parameter with a name only or an empty value. Default value is false.
     */
    Object allowEmptyValue

    /**
     * Required if type is "array". Describes the type of items in the array.
     */
    Object items

    /**
     * Determines the format of the array if type array is used. Possible values are:
     *
     * csv - comma separated values foo,bar.
     * ssv - space separated values foo bar.
     * tsv - tab separated values foo\tbar.
     * pipes - pipe separated values foo|bar.
     * multi - corresponds to multiple parameter instances instead of multiple values for a single instance foo=bar&foo=baz. This is valid only for parameters in "query" or "formData".
     *
     * Default value is csv.
     */
    Object collectionFormat

    Object default_

    // Validation

    Object maximum

    Object exclusiveMaximum

    Object minimum

    Object exclusiveMinimum

    Object maxLength

    Object minLength

    Object pattern

    Object maxItems

    Object minItems

    Object uniqueItems

    Object enum_

    Object multipleOf
}
