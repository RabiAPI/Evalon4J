package com.evalon4j.java

import com.evalon4j.Evalon4JConfiguration
import com.evalon4j.frameworks.openapi_annotations.OpenAPIDefinition
import com.evalon4j.frameworks.swagger_annotations.SwaggerDefinition

class JavaModule {
    String moduleName = ""

    SwaggerDefinition swaggerDefinition = null

    OpenAPIDefinition openAPIDefinition = null

    Evalon4JConfiguration cfg = new Evalon4JConfiguration()
}