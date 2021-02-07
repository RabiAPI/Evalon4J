package com.evalon4j.frameworks.swagger_annotations

import com.evalon4j.frameworks.Annotation

/**
 * High-level metadata for a Swagger definition - see
 * https://github.com/OAI/OpenAPI-Specification/blob/master/versions/2.0.md#infoObject
 *
 * @since 1.5.0
 */
class Info extends Annotation {
    /**
     * The title of the API.
     *
     * @return the title of the API
     */
    String title = "";

    /**
     * The version of your API.
     *
     * @return the version of your API
     */
    String version = "";

    /**
     * An optional description of the API.
     *
     * @return an optional description of the API
     */
    String description= "";

    /**
     * An optional terms of service for this API.
     *
     * @return an optional terms of service for this API
     */
    String termsOfService= "";

    /**
     * Optional contact information for this API.
     *
     * @return optional contact information for this API
     */
    Contact contact

    /**
     * Optional license information for this API.
     *
     * @return optional license information for this API
     */
    License license

    /**
     * Optional list of extensions for this API.
     *
     * @return optional list of extensions for this API
     * @see com.evalon4j.framework.swagger.v2.Extension
     */
    Extension[] extensions
}
