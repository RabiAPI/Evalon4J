package com.evalon4j.frameworks.spring.mappings

import com.evalon4j.frameworks.Annotation

class SpringMapping extends Annotation {
    /**
     * Assign a name to this mapping.
     * <p><b>Supported at the type level as well as at the method level!</b>
     * When used on both levels, a combined name is derived by concatenation
     * with "#" as separator.
     * @see org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder
     * @see org.springframework.web.servlet.handler.HandlerMethodMappingNamingStrategy
     */
    String name = ""

    /**
     * The primary mapping expressed by this annotation.
     * <p>This is an alias for {@link #path}. For example,
     * {@code @RequestMapping("/foo")} is equivalent to
     * {@code @RequestMapping(path="/foo")}.
     * <p><b>Supported at the type level as well as at the method level!</b>
     * When used at the type level, all method-level mappings inherit
     * this primary mapping, narrowing it for a specific handler method.
     * <p><strong>NOTE</strong>: A handler method that is not mapped to any path
     * explicitly is effectively mapped to an empty path.
     */
    String[] value = []

    /**
     * The path mapping URIs (e.g. {@code "/profile"}).
     * <p>Ant-style path patterns are also supported (e.g. {@code "/profile/**"}).
     * At the method level, relative paths (e.g. {@code "edit"}) are supported
     * within the primary mapping expressed at the type level.
     * Path mapping URIs may contain placeholders (e.g. <code>"/${profile_path}"</code>).
     * <p><b>Supported at the type level as well as at the method level!</b>
     * When used at the type level, all method-level mappings inherit
     * this primary mapping, narrowing it for a specific handler method.
     * <p><strong>NOTE</strong>: A handler method that is not mapped to any path
     * explicitly is effectively mapped to an empty path.
     * @since 4.2
     */
    String[] path = []

    /**
     * The parameters of the mapped request, narrowing the primary mapping.
     * <p>Same format for any environment: a sequence of "myParam=myValue" style
     * expressions, with a request only mapped if each such parameter is found
     * to have the given value. Expressions can be negated by using the "!=" operator,
     * as in "myParam!=myValue". "myParam" style expressions are also supported,
     * with such parameters having to be present in the request (allowed to have
     * any value). Finally, "!myParam" style expressions indicate that the
     * specified parameter is <i>not</i> supposed to be present in the request.
     * <p><b>Supported at the type level as well as at the method level!</b>
     * When used at the type level, all method-level mappings inherit
     * this parameter restriction (i.e. the type-level restriction
     * gets checked before the handler method is even resolved).
     * <p>Parameter mappings are considered as restrictions that are enforced at
     * the type level. The primary path mapping (i.e. the specified URI value)
     * still has to uniquely identify the target handler, with parameter mappings
     * simply expressing preconditions for invoking the handler.
     */
    String[] params = []

    /**
     * The headers of the mapped request, narrowing the primary mapping.
     * <p>Same format for any environment: a sequence of "My-Header=myValue" style
     * expressions, with a request only mapped if each such header is found
     * to have the given value. Expressions can be negated by using the "!=" operator,
     * as in "My-Header!=myValue". "My-Header" style expressions are also supported,
     * with such headers having to be present in the request (allowed to have
     * any value). Finally, "!My-Header" style expressions indicate that the
     * specified header is <i>not</i> supposed to be present in the request.
     * <p>Also supports media type wildcards (*), for headers such as Accept
     * and Content-Type. For instance,
     * <pre class="code">
     * &#064;RequestMapping(value = "/something", headers = "content-type=text/*")
     * </pre>
     * will match requests with a Content-Type of "text/html", "text/plain", etc.
     * <p><b>Supported at the type level as well as at the method level!</b>
     * When used at the type level, all method-level mappings inherit
     * this header restriction (i.e. the type-level restriction
     * gets checked before the handler method is even resolved).
     * @see org.springframework.http.MediaType
     */
    String[] headers = []

    /**
     * Narrows the primary mapping by media types that can be consumed by the
     * mapped handler. Consists of one or more media types one of which must
     * match to the request {@code Content-Type} header. Examples:
     * <pre class="code">
     * consumes = "text/plain"
     * consumes = {"text/plain", "application/*"}* consumes = MediaType.TEXT_PLAIN_VALUE
     * </pre>
     * Expressions can be negated by using the "!" operator, as in
     * "!text/plain", which matches all requests with a {@code Content-Type}
     * other than "text/plain".
     * <p><b>Supported at the type level as well as at the method level!</b>
     * If specified at both levels, the method level consumes condition overrides
     * the type level condition.
     * @see org.springframework.http.MediaType* @see javax.servlet.http.HttpServletRequest#getContentType()
     */
    String[] consumes = []

    /**
     * Narrows the primary mapping by media types that can be produced by the
     * mapped handler. Consists of one or more media types one of which must
     * be chosen via content negotiation against the "acceptable" media types
     * of the request. Typically those are extracted from the {@code "Accept"}
     * header but may be derived from query parameters, or other. Examples:
     * <pre class="code">
     * produces = "text/plain"
     * produces = {"text/plain", "application/*"}* produces = MediaType.TEXT_PLAIN_VALUE
     * produces = "text/plain;charset=UTF-8"
     * </pre>
     * <p>If a declared media type contains a parameter (e.g. "charset=UTF-8",
     * "type=feed", "type=entry") and if a compatible media type from the request
     * has that parameter too, then the parameter values must match. Otherwise
     * if the media type from the request does not contain the parameter, it is
     * assumed the client accepts any value.
     * <p>Expressions can be negated by using the "!" operator, as in "!text/plain",
     * which matches all requests with a {@code Accept} other than "text/plain".
     * <p><b>Supported at the type level as well as at the method level!</b>
     * If specified at both levels, the method level produces condition overrides
     * the type level condition.
     * @see org.springframework.http.MediaType
     */
    String[] produces = []
}
