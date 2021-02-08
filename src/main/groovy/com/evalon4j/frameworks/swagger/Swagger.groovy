package com.evalon4j.frameworks.swagger
/**
 * comment
 *
 * @author whitecosm0s_
 */
class Swagger {
    /**
     * Required. Specifies the Swagger Specification version being used.
     *
     * It can be used by the Swagger UI and other clients to interpret the API listing.
     * The value MUST be "2.0".
     */
    String swagger = null

    /**
     * Required. Provides metadata about the API.
     * The metadata can be used by the clients if needed.
     */
    Info info = null

    /**
     * The host (name or ip) serving the API.
     *
     * This MUST be the host only and does not include the scheme nor sub-paths
     * It MAY include a port. If the host is not included, the host serving the documentation is to be used (including the port).
     * The host does not support path templating.
     */
    String host

    /**
     * The base path on which the API is served, which is relative to the host.
     *
     * If it is not included, the API is served directly under the host.
     * The value MUST start with a leading slash (/). The basePath does not support path templating.
     */
    String basePath

    /**
     * The transfer protocol of the API.
     *
     * Values MUST be from the list: "http", "https", "ws", "wss".
     * If the schemes is not included, the default scheme to be used is the one used to access the Swagger definition itself.
     */
    List<String> schemes

    /**
     * A list of MIME types the APIs can consume.
     *
     * This is global to all APIs but can be overridden on specific API calls.
     * Value MUST be as described under Mime Types.
     */
    List<String> consumes

    /**
     * A list of MIME types the APIs can produce.
     *
     * This is global to all APIs but can be overridden on specific API calls.
     * Value MUST be as described under Mime Types.
     */
    List<String> produces

    /**
     * Required. The available paths and operations for the API.
     */
    Map<String, PathItem> paths

    /**
     * An object to hold data types produced and consumed by operations.
     */
    Object definitions

    /**
     * An object to hold parameters that can be used across operations.
     * This property does not define global parameters for all operations.
     */
    Map<String, Parameter> parameters

    /**
     * An object to hold responses that can be used across operations.
     * This property does not define global responses for all operations.
     */
    Map<String, Response> responses

    /**
     * Security scheme definitions that can be used across the specification.
     */
    Object securityDefinitions

    /**
     * A declaration of which security schemes are applied for the API as a whole.
     *
     * The list of values describes alternative security schemes that can be used (that is, there is a logical OR between the security requirements).
     * Individual operations can override this definition.
     */
    Object security

    /**
     * A list of tags used by the specification with additional metadata.
     *
     * The order of the tags can be used to reflect on their order by the parsing tools.
     * Not all tags that are used by the Operation Object must be declared.
     * The tags that are not declared may be organized randomly or based on the tools' logic.
     * Each tag name in the list MUST be unique.
     */
    List<Tag> tags

    /**
     * Additional external documentation.
     */
    ExternalDocumentation externalDocs
}
