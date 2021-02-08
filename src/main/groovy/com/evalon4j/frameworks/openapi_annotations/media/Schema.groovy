package com.evalon4j.frameworks.openapi_annotations.media


import com.evalon4j.frameworks.Annotation
import com.evalon4j.frameworks.openapi.extensions.Extension
import com.evalon4j.java.types.JavaAbstractType

/**
 * The annotation may be used to define a Schema for a set of elements of the OpenAPI spec, and/or to define additional
 * properties for the schema. It is applicable e.g. to parameters, schema classes (aka "models"), properties of such
 * models, request and response content, header.
 *
 * <p>swagger-core resolver and swagger-jaxrs2 reader engine consider this annotation along with JAX-RS annotations,
 * element type and context as input to resolve the annotated element into an OpenAPI schema definition for such element.</p>
 * <p>The annotation may be used also to override partly (e.g. the name) or fully (e.g providing a completely different
 * representation) the schema of an element for example if a specific class is provided as value of {@link Schema#implementation()},
 * it will override the element type</p>
 *
 * <p>The annotation {@link ArraySchema} shall be used for array elements {@link ArraySchema} and {@link Schema} cannot
 * coexist</p>
 *
 * @see ArraySchema
 */
class Schema extends Annotation {
    /**
     * Provides a java class as implementation for this schema.  When provided, additional information in the Schema annotation (except for type information) will augment the java class after introspection.
     *
     * @return a class that implements this schema
     */
    JavaAbstractType implementation = null

    /**
     * Provides a java class to be used to disallow matching properties.
     *
     * @return a class with disallowed properties
     */
    JavaAbstractType not = null

    /**
     * Provides an array of java class implementations which can be used to describe multiple acceptable schemas.  If more than one match the derived schemas, a validation error will occur.
     *
     * @return the list of possible classes for a single match
     */
    List<JavaAbstractType> oneOf = []

    /**
     * Provides an array of java class implementations which can be used to describe multiple acceptable schemas.  If any match, the schema will be considered valid.
     *
     * @return the list of possible class matches
     */
    List<JavaAbstractType> anyOf = []

    /**
     * Provides an array of java class implementations which can be used to describe multiple acceptable schemas.  If all match, the schema will be considered valid
     *
     * @return the list of classes to match
     */
    List<JavaAbstractType> allOf = []

    /**
     * The name of the schema or property.
     *
     * @return the name of the schema
     */
    String name = ""

    /**
     * A title to explain the purpose of the schema.
     *
     * @return the title of the schema
     */
    String title = ""

    /**
     * Constrains a value such that when divided by the multipleOf, the remainder must be an integer.  Ignored if the value is 0.
     *
     * @return the multiplier constraint of the schema
     */
    double multipleOf = 0

    /**
     * Sets the maximum numeric value for a property.  Ignored if the value is an empty string.
     *
     * @return the maximum value for this schema
     */
    String maximum = ""

    /**
     * if true, makes the maximum value exclusive, or a less-than criteria.
     *
     * @return the exclusive maximum value for this schema
     */
    boolean exclusiveMaximum = false

    /**
     * Sets the minimum numeric value for a property.  Ignored if the value is an empty string or not a number.
     *
     * @return the minimum value for this schema
     */
    String minimum = ""

    /**
     * If true, makes the minimum value exclusive, or a greater-than criteria.
     *
     * @return the exclusive minimum value for this schema
     */
    boolean exclusiveMinimum = false

    /**
     * Sets the maximum length of a string value.  Ignored if the value is negative.
     *
     * @return the maximum length of this schema
     */
    int maxLength = Integer.MAX_VALUE

    /**
     * Sets the minimum length of a string value.  Ignored if the value is negative.
     *
     * @return the minimum length of this schema
     */
    int minLength = 0

    /**
     * A pattern that the value must satisfy. Ignored if the value is an empty string.
     *
     * @return the pattern of this schema
     */
    String pattern = ""

    /**
     * Constrains the number of arbitrary properties when additionalProperties is defined.  Ignored if value is 0.
     *
     * @return the maximum number of properties for this schema
     */
    int maxProperties = 0

    /**
     * Constrains the number of arbitrary properties when additionalProperties is defined.  Ignored if value is 0.
     *
     * @return the minimum number of properties for this schema
     */
    int minProperties = 0

    /**
     * Allows multiple properties in an object to be marked as required.
     *
     * @return the list of required schema properties
     */
    String[] requiredProperties = []

    /**
     * Mandates that the annotated item is required or not.
     *
     * @return whether or not this schema is required
     */
    boolean required = false

    /**
     * A description of the schema.
     *
     * @return the schema's description
     *
     */
    String description = ""

    /**
     * Provides an optional override for the format.  If a consumer is unaware of the meaning of the format, they shall fall back to using the basic type without format.  For example, if \&quottype: integer, format: int128\&quot were used to designate a very large integer, most consumers will not understand how to handle it, and fall back to simply \&quottype: integer\&quot
     *
     * @return the schema's format
     */
    String format = ""

    /**
     * References a schema definition in an external OpenAPI document.
     *
     * @return a reference to this schema
     */
    String ref = ""

    /**
     * If true, designates a value as possibly null.
     *
     * @return whether or not this schema is nullable
     */
    boolean nullable = false

    /**
     * Sets whether the value should only be read during a response but not read to during a request.
     *
     * @deprecated As of 2.0.0, replaced by {@link #accessMode()}
     *
     * @return whether or not this schema is read only
     *
     */
    @Deprecated
    boolean readOnly = false

    /**
     * Sets whether a value should only be written to during a request but not returned during a response.
     *
     * @deprecated As of 2.0.0, replaced by {@link #accessMode()}
     *
     * @return whether or not this schema is write only
     */
    @Deprecated
    boolean writeOnly = false

    /**
     * Allows to specify the access mode (AccessMode.READ_ONLY, READ_WRITE)
     *
     * AccessMode.READ_ONLY: value will not be written to during a request but may be returned during a response.
     * AccessMode.WRITE_ONLY: value will only be written to during a request but not returned during a response.
     * AccessMode.READ_WRITE: value will be written to during a request and returned during a response.
     *
     * @return the accessMode for this schema (property)
     *
     */
    AccessMode accessMode = AccessMode.AUTO

    /**
     * Provides an example of the schema.  When associated with a specific media type, the example string shall be parsed by the consumer to be treated as an object or an array.
     *
     * @return an example of this schema
     */
    String example = ""

    /**
     * Additional external documentation for this schema.
     *
     * @return additional schema documentation
     */
//    ExternalDocumentation externalDocs =
//    @ExternalDocumentation()

    /**
     * Specifies that a schema is deprecated and should be transitioned out of usage.
     *
     * @return whether or not this schema is deprecated
     */
    boolean deprecated = false

    /**
     * Provides an override for the basic type of the schema.  Must be a valid type per the OpenAPI Specification.
     *
     * @return the type of this schema
     */
    String type = ""

    /**
     * Provides a list of allowable values.  This field map to the enum property in the OAS schema.
     *
     * @return a list of allowed schema values
     */
    String[] allowableValues = []

    /**
     * Provides a default value.
     *
     * @return the default value of this schema
     */
    String defaultValue = ""

    /**
     * Provides a discriminator property value.
     *
     * @return the discriminator property
     */
    String discriminatorProperty = ""

    /**
     * Provides discriminator mapping values.
     *
     * @return the discriminator mappings
     */
    DiscriminatorMapping[] discriminatorMapping = []

    /**
     * Allows schema to be marked as hidden.
     *
     * @return whether or not this schema is hidden
     */
    boolean hidden = false

    /**
     * Allows enums to be resolved as a reference to a scheme added to components section.
     *
     * @since 2.1.0* @return whether or not this must be resolved as a reference
     */
    boolean enumAsRef = false

    /**
     * An array of the sub types inheriting from this model.
     */
    Class<?>[] subTypes = []

    /**
     * The list of optional extensions
     *
     * @return an optional array of extensions
     */
    Extension[] extensions = []

    enum AccessMode {
        AUTO,
        READ_ONLY,
        WRITE_ONLY,
        READ_WRITE
    }
}
