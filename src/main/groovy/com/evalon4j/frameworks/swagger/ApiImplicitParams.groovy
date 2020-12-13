package com.evalon4j.frameworks.swagger

import com.evalon4j.frameworks.Annotation

/**
 * A wrapper to allow a list of multiple {@link ApiImplicitParam} objects.
 *
 * @see ApiImplicitParam
 */
class ApiImplicitParams extends Annotation {
    /**
     * A list of {@link com.evalon4j.frameworks.swagger.ApiImplicitParam}s available to the API operation.
     */
    ApiImplicitParam[] value = [];
}
