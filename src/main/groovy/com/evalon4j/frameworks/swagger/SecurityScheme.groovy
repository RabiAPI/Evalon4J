package com.evalon4j.frameworks.swagger

/**
 * comment
 *
 * @author whitecosm0s_
 */
class SecurityScheme {
    /**
     * Required. The type of the security scheme. Valid values are "basic", "apiKey" or "oauth2".
     */
    String type

    /**
     * A short description for security scheme.
     */
    String description

    /**
     * Required. The name of the header or query parameter to be used.
     */
    String name

    /**
     * Required The location of the API key. Valid values are "query" or "header".
     */
    String in_

    /**
     * Required. The flow used by the OAuth2 security scheme.
     *
     * Valid values are "implicit", "password", "application" or "accessCode".
     */
    String flow

    /**
     * Required. The authorization URL to be used for this flow. This SHOULD be in the form of a URL.
     */
    String authorizationUrl

    /**
     * Required. The token URL to be used for this flow. This SHOULD be in the form of a URL.
     */
    String tokenUrl

    /**
     * Required. The available scopes for the OAuth2 security scheme.
     */
    Map<String, String> scopes
}
