package com.evalon4j.frameworks.openapi_annotations

import com.evalon4j.frameworks.Annotation

/**
 * Container for repeatable {@link Parameter} annotation
 *
 * @see Parameter
 */
class Parameters extends Annotation {
    /**
     * An array of Parameters Objects for the operation
     *
     * @return the parameters
     */
    Parameter[] value = []
}
