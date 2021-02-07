package com.evalon4j.frameworks.swagger_annotations

import com.evalon4j.frameworks.Annotation

/**
 * Describes an OAuth2 authorization scope.
 * <p>
 * Used to define an authorization scope that is used by an operation for
 * a defined authorization scheme.
 * <p>
 * This annotation is not used directly and will not be parsed by Swagger. It should be used
 * within the {@link Authorization}.
 *
 * @see Authorization
 * @see ApiOperation
 * @see Api
 */
class AuthorizationScope extends Annotation {
    /**
     * The scope of the OAuth2 Authorization scheme to be used.
     * <p>
     * The scope should be previously declared in the Swagger Object's securityDefinition section.
     */
    String scope = "";

    /**
     * Not used in 1.5.X, kept for legacy support.
     */
    String description = "";
}
