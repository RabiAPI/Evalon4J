package com.evalon4j.frameworks.openapi_annotations.servers

import com.evalon4j.frameworks.Annotation

/**
 * The annotation may be applied at class or method level, or in {@link io.swagger.v3.oas.annotations.Operation#servers()} to define servers for the
 * single operation (when applied at method level) or for all operations of a class (when applied at class level).
 * <p>It can also be used in {@link io.swagger.v3.oas.annotations.OpenAPIDefinition#servers()} to define spec level servers.</p>
 *
 * @see <a target="_new" href="https://github.com/OAI/OpenAPI-Specification/blob/3.0.1/versions/3.0.1.md#serverObject">Server (OpenAPI specification)</a>
 * @see io.swagger.v3.oas.annotations.OpenAPIDefinition
 * @see io.swagger.v3.oas.annotations.Operation
 **/
class Server extends Annotation {
    /**
     * Required. A URL to the target host.
     * This URL supports Server Variables and may be relative, to indicate that the host location is relative to the location where the
     * OpenAPI definition is being served. Variable substitutions will be made when a variable is named in {brackets}.
     *
     * @return String url
     **/
    String url= "";

    /**
     * An optional string describing the host designated by the URL. CommonMark syntax MAY be used for rich text representation.
     *
     * @return String description
     **/
    String description= "";

    /**
     * An array of variables used for substitution in the server's URL template.
     *
     * @return array of ServerVariables
     **/
    ServerVariable[] variables= {};

    /**
     * The list of optional extensions
     *
     * @return an optional array of extensions
     */
//    Extension[] extensions= {};
}
