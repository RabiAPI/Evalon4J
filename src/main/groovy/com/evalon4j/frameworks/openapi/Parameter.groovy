package com.evalon4j.frameworks.openapi

import com.evalon4j.frameworks.Annotation
import com.evalon4j.frameworks.openapi.extensions.Extension
import com.evalon4j.frameworks.openapi.media.ArraySchema
import com.evalon4j.frameworks.openapi.media.Content
import com.evalon4j.frameworks.openapi.media.ExampleObject
import com.evalon4j.frameworks.openapi.media.Schema

/**
 * The annotation may be used on a method parameter to define it as a parameter for the operation, and/or to define
 * additional properties for the Parameter. It can also be used independently in {@link Operation#parameters()} or at
 * method level to add a parameter to the operation, even if not bound to any method parameter.
 *
 * <p>swagger-jaxrs2 reader engine considers this annotation along with JAX-RS annotations, parameter type and context
 * as input to resolve a method parameter into an OpenAPI Operation parameter.</p>
 *
 * <p>For method parameters bound to the request body, see {@link io.swagger.v3.oas.annotations.parameters.RequestBody}</p>
 *
 * @see <atarget="_new" href="https://github.com/OAI/OpenAPI-Specification/blob/3.0.1/versions/3.0.1.md#parameterObject"     >     Parameter (OpenAPI specification)</a>
 * @see io.swagger.v3.oas.annotations.parameters.RequestBody* @see Operation* @see Schema*     */
class Parameter extends Annotation {
    /**
     * The name of the parameter.
     *
     * @return the parameter's name
     * */
    String name = ""

    /**
     * The location of the parameter.  Possible values are "query", "header", "path" or "cookie".  Ignored when empty string.
     *
     * @return the parameter's location
     * */
    ParameterIn in_ = ParameterIn.DEFAULT

    /**
     * Additional description data to provide on the purpose of the parameter
     *
     * @return the parameter's description
     * */
    String description = ""

    /**
     * Determines whether this parameter is mandatory. If the parameter location is "path", this property is required and its value must be true. Otherwise, the property may be included and its default value is false.
     *
     * @return whether or not the parameter is required
     * */
    boolean required = false

    /**
     * Specifies that a parameter is deprecated and should be transitioned out of usage.
     *
     * @return whether or not the parameter is deprecated
     * */
    boolean deprecated = false

    /**
     * When true, allows sending an empty value.  If false, the parameter will be considered \&quot;null\&quot; if no value is present.  This may create validation errors when the parameter is required.
     *
     * @return whether or not the parameter allows empty values
     * */
    boolean allowEmptyValue = false

    /**
     * Describes how the parameter value will be serialized depending on the type of the parameter value. Default values (based on value of in): for query - form; for path - simple; for header - simple; for cookie - form.  Ignored if the properties content or array are specified.
     *
     * @return the style of the parameter
     * */
//    ParameterStyle style= ParameterStyle.DEFAULT;

    /**
     * When this is true, parameter values of type array or object generate separate parameters for each value of the array or key-value pair of the map. For other types of parameters this property has no effect. When style is form, the default value is true. For all other styles, the default value is false.  Ignored if the properties content or array are specified.
     *
     * @return whether or not to expand individual array members
     * */
//    Explode explode= Explode.DEFAULT;

    /**
     * Determines whether the parameter value should allow reserved characters, as defined by RFC3986. This property only applies to parameters with an in value of query. The default value is false.  Ignored if the properties content or array are specified.
     *
     * @return whether or not the parameter allows reserved characters
     * */
    boolean allowReserved = false

    /**
     * The schema defining the type used for the parameter.  Ignored if the properties content or array are specified.
     *
     * @return the schema of the parameter
     * */
    Schema schema = null

    /**
     * The schema of the array that defines this parameter.  Ignored if the property content is specified.
     *
     * @return the schema of the array
     */
    ArraySchema array = null

    /**
     * The representation of this parameter, for different media types.
     *
     * @return the content of the parameter
     * */
    Content[] content = []

    /**
     * Allows this parameter to be marked as hidden
     *
     * @return whether or not this parameter is hidden
     */
    boolean hidden = false

    /**
     * An array of examples  of the schema used to show the use of the associated schema.
     *
     * @return array of examples of the parameter
     * */
    ExampleObject[] examples = []

    /**
     * Provides an example of the schema.  When associated with a specific media type, the example string shall be parsed by the consumer to be treated as an object or an array.  Ignored if the properties examples, content or array are specified.
     *
     * @return an example of the parameter
     * */
    String example = ""

    /**
     * The list of optional extensions
     *
     * @return an optional array of extensions
     */
    Extension[] extensions = []

    /**
     * A reference to a parameter defined in components parameter.
     *
     * @since 2.0.3* @return the reference
     * */
    String ref = ""
}
