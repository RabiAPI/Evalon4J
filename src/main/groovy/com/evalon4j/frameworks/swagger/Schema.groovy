package com.evalon4j.frameworks.swagger

/**
 * comment
 *
 * @author whitecosm0s_
 */
class Schema extends Reference {
    /**
     * Adds support for polymorphism.
     *
     * The discriminator is the schema property name that is used to differentiate between other schema that inherit this schema.
     * The property name used MUST be defined at this schema and it MUST be in the required property list.
     * When used, the value MUST be the name of this schema or any schema that inherits it.
     */
    String discriminator

    /**
     * Relevant only for Schema "properties" definitions.
     *
     * Declares the property as "read only".
     * This means that it MAY be sent as part of a response but MUST NOT be sent as part of the request.
     * Properties marked as readOnly being true SHOULD NOT be in the required list of the defined schema.
     * Default value is false.
     */
    boolean readOnly = false

    /**
     * This MAY be used only on properties schemas.
     *
     * It has no effect on root schemas.
     * Adds Additional metadata to describe the XML representation format of this property.
     */
    XML xml

    /**
     * Additional external documentation for this schema.
     */
    ExternalDocumentation externalDocs

    /**
     * A free-form property to include an example of an instance for this schema.
     */
    Object example

    // Validation


}
