package com.evalon4j.frameworks.openapi_annotations.extensions

import com.evalon4j.frameworks.Annotation

/**
 * Container for repeatable {@link Extension} annotation
 *
 * @see Extension
 */
class Extensions extends Annotation {
    /**
     * An array of Extension annotations
     *
     * @return the array of the extensions
     **/
    Extension[] value = [];
}
