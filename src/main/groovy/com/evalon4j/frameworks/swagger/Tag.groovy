package com.evalon4j.frameworks.swagger

import com.evalon4j.frameworks.Annotation

/**
 * A definition level Tag object see https://github.com/OAI/OpenAPI-Specification/blob/master/versions/2.0.md#tag-object
 *
 * @since 1.5.0
 */
class Tag extends Annotation {
    /**
     * The name of this tag.
     *
     * @return the name of this tag
     */
    String name = ""

    /**
     * Optional description of the tag.
     *
     * @return an optional description of what this tag means
     */
    String description = ""

    /**
     * Optional reference to external documentation for this tag.
     *
     * @return an optional reference to external documentation for this tag
     */
    ExternalDocs externalDocs

    /**
     * A list of extensions associated with this tag.
     *
     * @return a list of extensions associated with this tag
     */
    Extension[] extensions = []
}
