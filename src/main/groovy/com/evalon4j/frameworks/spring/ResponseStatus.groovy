package com.evalon4j.frameworks.spring

import com.evalon4j.frameworks.Annotation

class ResponseStatus extends Annotation {
    /**
     * Alias for {@link #code}.
     */
    HttpStatus value= HttpStatus.INTERNAL_SERVER_ERROR;

    /**
     * The status <em>code</em> to use for the response.
     * <p>Default is {@link HttpStatus#INTERNAL_SERVER_ERROR}, which should
     * typically be changed to something more appropriate.
     * @since 4.2
     * @see javax.servlet.http.HttpServletResponse#setStatus(int)
     * @see javax.servlet.http.HttpServletResponse#sendError(int)
     */
    HttpStatus code= HttpStatus.INTERNAL_SERVER_ERROR;

    /**
     * The <em>reason</em> to be used for the response.
     * @see javax.servlet.http.HttpServletResponse#sendError(int, String)
     */
    String reason= "";
}
