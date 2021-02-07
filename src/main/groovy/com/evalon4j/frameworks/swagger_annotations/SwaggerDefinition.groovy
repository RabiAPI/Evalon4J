package com.evalon4j.frameworks.swagger_annotations

import com.evalon4j.frameworks.Annotation

/**
 * Annotation that configures definition level metadata. Still missing are the following:
 * - Security Requirements
 * - Parameters
 * - Responses
 *
 * @since 1.5.0
 */
class SwaggerDefinition extends Annotation {
    SwaggerDefinition() {

    }

    /**
     * The host to specify in the generated Swagger definition.
     *
     * @return the host to specify in the generated Swagger definition - keep empty for default
     */
    String host = "";

    /**
     * The basePath to specify in the generated Swagger definition.
     *
     * @return the basePath to specify in the generated Swagger definition - keep empty for default
     */
    String basePath = "";

    /**
     * Global level consumes for this swagger definition.
     * <p>
     * These will be added to all api definitions that don't have local overrides - see
     * https://github.com/OAI/OpenAPI-Specification/blob/master/versions/2.0.md#swagger-object
     *
     * @return a list of global level consumes.
     */
    String[] consumes = "";

    /**
     * Global level produces for this swagger definition.
     * <p>
     * These will be added to all api definitions that don't have local overrides - see
     * https://github.com/OAI/OpenAPI-Specification/blob/master/versions/2.0.md#swagger-object
     *
     * @return a list of global level consumes
     */
    String[] produces = "";

    /**
     * The transfer protocol of the API.
     * <p>
     * Setting this to Scheme.DEFAULT will result in the result being generated from the hosting container.
     *
     * @return list of supported transfer protocols, keep empty for default
     */
    Scheme[] schemes = Scheme.DEFAULT;

    /**
     * Global tags that can be used to tag individual Apis and ApiOperations.
     * <p>
     * See https://github.com/OAI/OpenAPI-Specification/blob/master/versions/2.0.md#tagObject
     *
     * @return list of globally defined tags
     */
    Tag[] tags = [];

    /**
     * Defintions for security schemes
     *
     * @return defintions for security schemes
     */
    SecurityDefinition securityDefinition = null;

    /**
     * General metadata for this Swagger definition.
     * <p>
     * See https://github.com/OAI/OpenAPI-Specification/blob/master/versions/2.0.md#infoObject
     *
     * @return general metadata for this Swagger definition
     */
    Info info = null

    /**
     * Reference to external documentation for this Swagger definition.
     *
     * @return a reference to external documentation
     */
    ExternalDocs externalDocs = null
}
