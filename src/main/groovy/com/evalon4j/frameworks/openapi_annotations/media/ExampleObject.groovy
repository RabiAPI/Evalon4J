package com.evalon4j.frameworks.openapi_annotations.media

import com.evalon4j.frameworks.Annotation
import com.evalon4j.frameworks.openapi.extensions.Extension

/**
 * The annotation may be used to add one or more examples to the definition of a parameter, request or response content,
 * by defining it as field {@link io.swagger.v3.oas.annotations.Parameter#examples()} or {@link Content#examples()}
 *
 * @see <atarget="_new" href="https://github.com/OAI/OpenAPI-Specification/blob/3.0.1/versions/3.0.1.md#exampleObject" > Example (OpenAPI specification)</a>
 * */
class ExampleObject extends Annotation {
    /**
     * A unique name to identify this particular example
     *
     * @return the name of the example
     * */
    String name = ""

    /**
     * A brief summary of the purpose or context of the example
     *
     * @return a summary of the example
     * */
    String summary = ""

    /**
     * A string representation of the example.  This is mutually exclusive with the externalValue property, and ignored if the externalValue property is specified.  If the media type associated with the example allows parsing into an object, it may be converted from a string
     *
     * @return the value of the example
     * */
    String value = ""

    /**
     * A URL to point to an external document to be used as an example.  This is mutually exclusive with the value property.
     *
     * @return an external URL of the example
     * */
    String externalValue = ""

    /**
     * The list of optional extensions
     *
     * @return an optional array of extensions
     */
    Extension[] extensions = []

    /**
     * A reference to a example defined in components examples.
     *
     * @since 2.0.3* @return the reference
     * */
    String ref = ""

    /**
     * A description of the purpose or context of the example
     *
     * @since 2.1.0* @return a description of the example
     * */
    String description = ""
}
