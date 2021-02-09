package com.evalon4j.frameworks.openapi_annotations.media

import com.evalon4j.frameworks.Annotation
import com.evalon4j.frameworks.openapi_annotations.extensions.Extension

/**
 * The annotation may be used to define the content/media type  of a parameter, request or response, by defining it as
 * field {@link io.swagger.v3.oas.annotations.Parameter#content()}, {@link io.swagger.v3.oas.annotations.parameters.RequestBody#content()} or {@link io.swagger.v3.oas.annotations.responses.ApiResponse#content()}.
 * <p>If {@link Content#schema()} is defined, swagger-jaxrs2 reader engine will consider it along with
 * JAX-RS annotations, element type and context as input to resolve the annotated element into an OpenAPI schema
 * definition for such element.</p>
 *
 * @see <atarget="_new" href="https://github.com/OAI/OpenAPI-Specification/blob/3.0.1/versions/3.0.1.md#exampleObject" > Example (OpenAPI specification)</a>
 * @see Schema* @see io.swagger.v3.oas.annotations.Parameter* @see io.swagger.v3.oas.annotations.responses.ApiResponse* @see io.swagger.v3.oas.annotations.parameters.RequestBody* */
class Content extends Annotation {
    /**
     * The media type that this object applies to.
     *
     * @return the media type value
     * */
    String mediaType = "";

    /**
     * An array of examples used to show the use of the associated schema.
     *
     * @return the list of examples
     * */
    ExampleObject[] examples = [];

    /**
     * The schema defining the type used for the content.
     *
     * @return the schema of this media type
     * */
    Schema schema = null;

    /**
     * The schema of the array that defines the type used for the content.
     *
     * @return the schema of the array
     */
    ArraySchema array = null;

    /**
     * An array of encodings
     * The key, being the property name, MUST exist in the schema as a property.
     *
     * @return the array of encodings
     */
    Encoding[] encoding = [];

    /**
     * The list of optional extensions
     *
     * @return an optional array of extensions
     */
    Extension[] extensions = [];
}
