package com.evalon4j.frameworks.swagger_annotations

import com.evalon4j.frameworks.Annotation


/**
 * License metadata available within the info section of a Swagger definition, see
 * https://github.com/OAI/OpenAPI-Specification/blob/master/versions/2.0.md#licenseObject
 *
 * @since 1.5.0
 */

class License extends Annotation {
    /**
     * The name of the license.
     *
     * @return the name of the license
     */
    String name = "";

    /**
     * An optional URL for the license.
     *
     * @return an optional URL for the license.
     */
    String url = "";
}
