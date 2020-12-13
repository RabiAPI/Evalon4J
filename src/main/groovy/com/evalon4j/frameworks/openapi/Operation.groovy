package com.evalon4j.frameworks.openapi

import com.evalon4j.frameworks.Annotation
import com.evalon4j.frameworks.openapi.parameters.RequestBody
import com.evalon4j.frameworks.openapi.responses.ApiResponse
import com.evalon4j.frameworks.openapi.servers.Server

/**
 * The annotation may be used to define a resource method as an OpenAPI Operation, and/or to define additional
 * properties for the Operation.
 *
 * <p>Note: swagger-jaxrs2 reader engine includes by default also methods of scanned resources which are not annotated
 * with @Operation, as long as a jax-rs @Path is defined at class and/or method level, together with the http method
 * annotation (@GET, @POST, etc).</p>
 * <p>This behaviour is controlled by configuration property `scanAllResources` which defaults to true. By setting this
 * flag to false only @{@link Operation} annotated methods are considered.</p>
 *
 * <p>The following fields can also alternatively be defined at method level (as repeatable annotations in case of arrays),
 * in this case method level annotations take precedence over @{@link Operation} annotation fields:</p>
 *
 * <ul>
 * <li>tags: @{@link io.swagger.v3.oas.annotations.tags.Tag}</li>
 * <li>externalDocs: @{@link ExternalDocumentation}</li>
 * <li>parameters: @{@link Parameter}</li>
 * <li>responses: @{@link ApiResponse}</li>
 * <li>security: @{@link SecurityRequirement}</li>
 * <li>servers: @{@link Server}</li>
 * <li>extensions: @{@link Extension}</li>
 * <li>hidden: @{@link Hidden}</li>
 * </ul>
 *
 * @see <atarget="_new" href="https://github.com/OAI/OpenAPI-Specification/blob/3.0.1/versions/3.0.1.md#operationObject"  >  Operation (OpenAPI specification)</a>
 * */
class Operation extends Annotation {
    /**
     * The HTTP method for this operation.
     *
     * @return the HTTP method of this operation
     * */
    String method = ""

    /**
     * Tags can be used for logical grouping of operations by resources or any other qualifier.
     *
     * @return the list of tags associated with this operation
     * */
    String[] tags = []

    /**
     * Provides a brief description of this operation. Should be 120 characters or less for proper visibility in Swagger-UI.
     *
     * @return a summary of this operation
     * */
    String summary = ""

    /**
     * A verbose description of the operation.
     *
     * @return a description of this operation
     * */
    String description = ""

    /**
     * Request body associated to the operation.
     *
     * @return a request body.
     */
    RequestBody requestBody = null

    /**
     * Additional external documentation for this operation.
     *
     * @return additional documentation about this operation
     * */
//    ExternalDocumentation externalDocs= @ExternalDocumentation();

    /**
     * The operationId is used by third-party tools to uniquely identify this operation.
     *
     * @return the ID of this operation
     * */
    String operationId = ""

    /**
     * An optional array of parameters which will be added to any automatically detected parameters in the method itself.
     *
     * @return the list of parameters for this operation
     * */
    Parameter[] parameters = []

    /**
     * The list of possible responses as they are returned from executing this operation.
     *
     * @return the list of responses for this operation
     * */
    ApiResponse[] responses = []

    /**
     * Allows an operation to be marked as deprecated.  Alternatively use the @Deprecated annotation
     *
     * @return whether or not this operation is deprecated
     * */
    boolean deprecated = false

    /**
     * A declaration of which security mechanisms can be used for this operation.
     *
     * @return the array of security requirements for this Operation
     */
//    SecurityRequirement[] security= {};

    /**
     * An alternative server array to service this operation.
     *
     * @return the list of servers hosting this operation
     * */
    Server[] servers = []

    /**
     * The list of optional extensions
     *
     * @return an optional array of extensions
     */
//    Extension[] extensions= {};

    /**
     * Allows this operation to be marked as hidden
     *
     * @return whether or not this operation is hidden
     */
    boolean hidden = false

    /**
     * Ignores JsonView annotations while resolving operations and types.
     *
     * @return whether or not to ignore JsonView annotations
     */
    boolean ignoreJsonView = false
}
