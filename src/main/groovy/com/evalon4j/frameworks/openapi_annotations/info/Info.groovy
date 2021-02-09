package com.evalon4j.frameworks.openapi_annotations.info

import com.evalon4j.frameworks.Annotation
import com.evalon4j.frameworks.openapi_annotations.extensions.Extension

class Info extends Annotation {
    /**
     * The title of the application.
     *
     * @return the application's title
     * */
    String title = ""

    /**
     * A short description of the application. CommonMark syntax can be used for rich text representation.
     *
     * @return the application's description
     * */
    String description = ""

    /**
     * A URL to the Terms of Service for the API. Must be in the format of a URL.
     *
     * @return the application's terms of service
     * */
    String termsOfService = ""

    /**
     * The contact information for the exposed API.
     *
     * @return a contact for the application
     * */
    Contact contact

    /**
     * The license information for the exposed API.
     *
     * @return the license of the application
     * */
    License license

    /**
     * The version of the API definition.
     *
     * @return the application's version
     * */
    String version = ""

    /**
     * The list of optional extensions
     *
     * @return an optional array of extensions
     */
    Extension[] extensions
}
