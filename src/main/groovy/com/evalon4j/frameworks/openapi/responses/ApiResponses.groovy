package com.evalon4j.frameworks.openapi.responses

import com.evalon4j.frameworks.Annotation
import com.evalon4j.frameworks.openapi.extensions.Extension

class ApiResponses extends Annotation {
    /**
     * An array of ApiResponse annotations
     *
     * @return the array of the ApiResponse
     **/
    ApiResponse[] value = []

    /**
     * The list of optional extensions
     *
     * @return an optional array of extensions
     */
    Extension[] extensions = []
}
