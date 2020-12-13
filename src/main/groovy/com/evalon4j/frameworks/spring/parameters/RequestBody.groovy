package com.evalon4j.frameworks.spring.parameters

import com.evalon4j.frameworks.Annotation

/**
 * Annotation indicating a method parameter should be bound to the body of the web request.
 * The body of the request is passed through an {@link HttpMessageConverter} to resolve the
 * method argument depending on the content type of the request. Optionally, automatic
 * validation can be applied by annotating the argument with {@code @Valid}.
 *
 * <p>Supported for annotated handler methods.
 *
 * @author Arjen Poutsma
 * @since 3.0* @see RequestHeader* @see ResponseBody* @see org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter
 */
class RequestBody extends Annotation {
    /**
     * Whether body content is required.
     * <p>Default is {@code true}, leading to an exception thrown in case
     * there is no body content. Switch this to {@code false} if you prefer
     * {@code null} to be passed when the body content is {@code null}.
     * @since 3.2
     */
    boolean required = true;
}
