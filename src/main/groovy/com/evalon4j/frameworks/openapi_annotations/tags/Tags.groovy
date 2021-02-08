package com.evalon4j.frameworks.openapi_annotations.tags

import com.evalon4j.frameworks.Annotation

/**
 * Container for repeatable {@link Tag} annotation
 *
 * @see Tag
 */
class Tags extends Annotation {
    /**
     * An array of Tag annotation objects which hold metadata for the API
     *
     * @return array of Tags
     */
    Tag[] value = []
}
