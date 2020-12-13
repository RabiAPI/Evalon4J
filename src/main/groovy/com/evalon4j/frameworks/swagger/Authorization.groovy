package com.evalon4j.frameworks.swagger

import com.evalon4j.frameworks.Annotation

/**
 * Defines an authorization scheme to be used on a resource or an operation.
 * <p>
 * The authorization scheme used needs to be declared at the Swagger root level first.
 * <p>
 * This annotation is not used directly and will not be parsed by Swagger. It should be used
 * within either {@link Api} or {@link ApiOperation}.
 *
 * @see ApiOperation
 * @see Api
 */
class Authorization extends Annotation {
    /**
     * The name of the authorization scheme to be used on this resource/operation.
     * <p>
     * The name must be defined in the Resource Listing's authorization section,
     */
    String value = "";

    /**
     * The scopes to be used if the authorization scheme is OAuth2.
     *
     * @see com.evalon4j.framework.swagger.v2.AuthorizationScope
     */
    AuthorizationScope[] scopes = []
}
