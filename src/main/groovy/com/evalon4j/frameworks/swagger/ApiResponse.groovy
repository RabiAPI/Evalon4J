package com.evalon4j.frameworks.swagger

import com.evalon4j.frameworks.Annotation
import com.evalon4j.java.types.JavaAbstractType

/**
 * Describes a possible response of an operation.
 * <p>
 * This can be used to describe possible success and error codes from your REST API call.
 * You may or may not use this to describe the return type of the operation (normally a
 * successful code), but the successful response should be described as well using the
 * {@link ApiOperation}.
 * <p>
 * This annotation can be applied at method or class level; class level annotations will
 * be parsed only if an @ApiResponse annotation with the same code is not defined at method
 * level or in thrown Exception
 * <p>
 * If your API has uses a different response class for these responses, you can describe them
 * here by associating a response class with a response code.
 * Note, Swagger does not allow multiple response types for a single response code.
 * <p>
 * This annotation is not used directly and will not be parsed by Swagger. It should be used
 * within the {@link ApiResponses}.
 *
 * @see ApiOperation* @see ApiResponses
 */
class ApiResponse extends Annotation {
    /**
     * The HTTP status code of the response.
     * <p>
     * The value should be one of the formal <a target="_blank" href="http://www.w3.org/Protocols/rfc2616/rfc2616-sec10.html">HTTP Status Code Definitions</a>.
     */
    int code

    /**
     * Human-readable message to accompany the response.
     */
    String message

    /**
     * Optional response class to describe the payload of the message.
     * <p>
     * Corresponds to the `schema` field of the response message object.
     */
    JavaAbstractType response = null

    /**
     * Specifies a reference to the response type. The specified reference can be either local or remote and
     * will be used as-is, and will override any specified response() class.
     */
    String reference = "" // ignore

    /**
     * A list of possible headers provided alongside the response.
     *
     * @return a list of response headers.
     */
    ResponseHeader[] responseHeaders = []

    /**
     * Declares a container wrapping the response.
     * <p>
     * Valid values are "List", "Set" or "Map". Any other value will be ignored.
     */
    String responseContainer = "" // 构建引用时并入response

    /**
     * Examples for the response.
     *
     * @since 1.5.20*
     * @return
     */
    Example examples = null
}
