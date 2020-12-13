package com.evalon4j.frameworks.openapi.callbacks

import com.evalon4j.frameworks.Annotation

/**
 * Container for repeatable {@link Callback} annotation
 *
 * @see Callback
 */
class Callbacks extends Annotation {
    /**
     * An array of Callback annotations which are a map of possible out-of band callbacks related to the parent operation
     *
     * @return the array of the callbacks
     * */
    Callback[] value = [];
}
