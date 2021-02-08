package com.evalon4j.frameworks.openapi_annotations.tags

import com.evalon4j.frameworks.Annotation

/**
 * The annotation may be applied at class or method level, or in {@link io.swagger.v3.oas.annotations.Operation#tags()} to define tags for the
 * single operation (when applied at method level) or for all operations of a class (when applied at class level).
 * <p>It can also be used in {@link io.swagger.v3.oas.annotations.OpenAPIDefinition#tags()} to define spec level tags.</p>
 * <p>When applied at method or class level, if only a name is provided, the tag will be added to operation only;
 * if additional fields are also defined, like description or externalDocs, the Tag will also be added to openAPI.tags
 * field</p>
 *
 * @see <a target="_new" href="https://github.com/OAI/OpenAPI-Specification/blob/3.0.1/versions/3.0.1.md#tagObject">Tag (OpenAPI specification)</a>
 * @see io.swagger.v3.oas.annotations.OpenAPIDefinition
 **/
class Tag extends Annotation {
    /**
     * The name of this tag.
     *
     * @return the name of this tag
     */
    String name = "";

    /**
     * A short description for this tag.
     *
     * @return the description of this tag
     */
    String description= "";

    /**
     * Additional external documentation for this tag.
     *
     * @return the external documentation for this tag
     */
//    ExternalDocumentation externalDocs= @ExternalDocumentation();

    /**
     * The list of optional extensions
     *
     * @return an optional array of extensions
     */
//    Extension[] extensions= {};
}
