package com.evalon4j.frameworks.swagger

import com.evalon4j.frameworks.Annotation

/**
 * A wrapper to allow a list of multiple {@link ApiResponse} objects.
 * <p>
 * If you need to describe a single {@link ApiResponse}, you still
 * must use this annotation and wrap the {@code @ApiResponse} in an array.
 *
 * @see ApiResponse
 */
class ApiResponses extends Annotation {
    ApiResponses() {

    }

    /**
     * A list of {@link com.evalon4j.framework.swagger.v2.ApiResponse}s provided by the API operation.
     */
    ApiResponse[] value = [];
}
