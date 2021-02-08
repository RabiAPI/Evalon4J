package com.evalon4j.frameworks.swagger

/**
 * comment
 *
 * @author whitecosm0s_
 */
class Info {
    /**
     * 	Required. The title of the application.
     */
    String title

    /**
     * 	A short description of the application. GFM syntax can be used for rich text representation.
     */
    String description

    /**
     * 	The Terms of Service for the API.
     */
    String termsOfService

    /**
     * The contact information for the exposed API.
     */
    Contact contact

    /**
     * The license information for the exposed API.
     */
    License license

    /**
     * Required Provides the version of the application API (not to be confused with the specification version).
     */
    String version
}
