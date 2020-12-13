package com.evalon4j.frameworks.swagger

import com.evalon4j.frameworks.Annotation

/**
 * Contact metadata available within the info section of a Swagger definition - see
 * https://github.com/OAI/OpenAPI-Specification/blob/master/versions/2.0.md#contactObject
 *
 * @since 1.5.0
 */
class Contact extends Annotation {
    /**
     * The name of the contact.
     *
     * @return the name of the contact
     */
    String name = "";

    /**
     * Optional URL associated with this contact.
     *
     * @return an optional URL associated with this contact
     */
    String url = "";

    /**
     * Optional email for this contact.
     *
     * @return an optional email for this contact
     */
    String email = "";
}
