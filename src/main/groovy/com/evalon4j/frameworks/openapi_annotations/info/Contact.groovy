package com.evalon4j.frameworks.openapi_annotations.info

import com.evalon4j.frameworks.Annotation
import com.evalon4j.frameworks.openapi_annotations.extensions.Extension

class Contact extends Annotation {
    /**
     * The identifying name of the contact person/organization.
     *
     * @return the name of the contact
     * */
    String name = ""

    /**
     * The URL pointing to the contact information. Must be in the format of a URL.
     *
     * @return the URL of the contact
     * */
    String url = ""

    /**
     * The email address of the contact person/organization. Must be in the format of an email address.
     *
     * @return the email address of the contact
     * */
    String email = ""

    /**
     * The list of optional extensions
     *
     * @return an optional array of extensions
     */
    Extension[] extensions
}
