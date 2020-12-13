package com.evalon4j.frameworks.openapi.info

import com.evalon4j.frameworks.Annotation
import com.evalon4j.frameworks.openapi.extensions.Extension

class License extends Annotation {
    /**
     * The license name used for the API.
     *
     * @return the name of the license
     * */
    String name = ""

    /**
     * A URL to the license used for the API. MUST be in the format of a URL.
     *
     * @return the URL of the license
     * */
    String url = ""

    /**
     * The list of optional extensions
     *
     * @return an optional array of extensions
     */
    Extension[] extensions
}
