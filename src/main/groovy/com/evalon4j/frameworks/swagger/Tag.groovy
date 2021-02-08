package com.evalon4j.frameworks.swagger

/**
 * comment
 *
 * @author whitecosm0s_
 */
class Tag {
    /**
     * Required. The name of the tag.
     */
    String name

    /**
     * A short description for the tag. GFM syntax can be used for rich text representation.
     */
    String description

    /**
     * Additional external documentation for this tag.
     */
    ExternalDocumentation externalDocs
}
