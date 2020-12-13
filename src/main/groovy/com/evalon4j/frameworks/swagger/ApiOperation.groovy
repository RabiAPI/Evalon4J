package com.evalon4j.frameworks.swagger

import com.evalon4j.frameworks.Annotation
import com.evalon4j.java.types.JavaAbstractType
import com.evalon4j.json.JsonStruct

/**
 * Describes an operation or typically a HTTP method against a specific path.
 * <p>
 * Operations with equivalent paths are grouped in a single Operation Object.
 * A combination of a HTTP method and a path creates a unique operation.
 */
class ApiOperation extends Annotation {
    ApiOperation() {

    }

    /**
     * Corresponds to the `summary` field of the operation.
     * <p>
     * Provides a brief description of this operation. Should be 120 characters or less
     * for proper visibility in Swagger-UI.
     */
    public String value = ""

    /**
     * Corresponds to the 'notes' field of the operation.
     * <p>
     * A verbose description of the operation.
     */
    public String notes = ""

    /**
     * A list of tags for API documentation control.
     * <p>
     * Tags can be used for logical grouping of operations by resources or any other qualifier.
     * A non-empty value will override the value received from {@link Api#value()} or {@link Api#tags()}
     * for this operation.
     *
     * @since 1.5.2-M1
     */
    public String[] tags = ""

    /**
     * The response type of the operation.
     * <p>
     * In JAX-RS applications, the return type of the method would automatically be used, unless it is
     * {@code javax.ws.rs.core.Response}. In that case, the operation return type would default to `void`
     * as the actual response type cannot be known.
     * <p>
     * Setting this property would override any automatically-derived data type.
     * <p>
     * If the value used is a class representing a primitive ({@code Integer}, {@code Long}, ...)
     * the corresponding primitive type will be used.
     */
    JavaAbstractType response = null

    /**
     * Declares a container wrapping the response.
     * <p>
     * Valid values are "List", "Set" or "Map". Any other value will be ignored.
     */
    String responseContainer = ""

    /**
     * Specifies a reference to the response type. The specified reference can be either local or remote and
     * will be used as-is, and will override any specified response() class.
     */
    // public String responseReference = "" // ignore

    /**
     * Corresponds to the `method` field as the HTTP method used.
     * <p>
     * If not stated, in JAX-RS applications, the following JAX-RS annotations would be scanned
     * and used: {@code @GET}, {@code @HEAD}, {@code @POST}, {@code @PUT}, {@code @DELETE} and {@code @OPTIONS}.
     * Note that even though not part of the JAX-RS specification, if you create and use the {@code @PATCH} annotation,
     * it will also be parsed and used. If the httpMethod property is set, it will override the JAX-RS annotation.
     * <p>
     * For Servlets, you must specify the HTTP method manually.
     * <p>
     * Acceptable values are "GET", "HEAD", "POST", "PUT", "DELETE", "OPTIONS" and "PATCH".
     */
    public String httpMethod = ""

    /**
     * Not used in 1.5.X, kept for legacy support.
     */
    @Deprecated
    int position = 0

    /**
     * Corresponds to the `operationId` field.
     * <p>
     * The operationId is used by third-party tools to uniquely identify this operation. In Swagger 2.0, this is
     * no longer mandatory and if not provided will remain empty.
     */
    String nickname = ""

    /**
     * Corresponds to the `produces` field of the operation.
     * <p>
     * Takes in comma-separated values of content types.
     * For example, "application/json, application/xml" would suggest this operation
     * generates JSON and XML output.
     * <p>
     * For JAX-RS resources, this would automatically take the value of the {@code @Produces}
     * annotation if such exists. It can also be used to override the {@code @Produces} values
     * for the Swagger documentation.
     */
    String produces = ""

    /**
     * Corresponds to the `consumes` field of the operation.
     * <p>
     * Takes in comma-separated values of content types.
     * For example, "application/json, application/xml" would suggest this API Resource
     * accepts JSON and XML input.
     * <p>
     * For JAX-RS resources, this would automatically take the value of the {@code @Consumes}
     * annotation if such exists. It can also be used to override the {@code @Consumes} values
     * for the Swagger documentation.
     */
    String consumes = ""

    /**
     * Sets specific protocols (schemes) for this operation.
     * <p>
     * Comma-separated values of the available protocols. Possible values: http, https, ws, wss.
     *
     * @return the protocols supported by the operations under the resource.
     */
    String protocols = ""

    /**
     * Corresponds to the `security` field of the Operation Object.
     * <p>
     * Takes in a list of the authorizations (security requirements) for this operation.
     *
     * @return an array of authorizations required by the server, or a single, empty authorization value if not set.
     * @see com.evalon4j.framework.swagger.v2.Authorization
     */
    Authorization[] authorizations = []

    /**
     * Hides the operation from the list of operations.
     */
    boolean hidden = false

    /**
     * A list of possible headers provided alongside the response.
     *
     * @return a list of response headers.
     */
    ResponseHeader[] responseHeaders = []

    /**
     * The HTTP status code of the response.
     * <p>
     * The value should be one of the formal <a target="_blank" href="http://www.w3.org/Protocols/rfc2616/rfc2616-sec10.html">HTTP Status Code Definitions</a>.
     */
    int code = 200

    /**
     * @return an optional array of extensions
     */

    Extension[] extensions = []

    /**
     * Ignores JsonView annotations while resolving operations and types. For backward compatibility
     *
     */
    boolean ignoreJsonView = false
}
