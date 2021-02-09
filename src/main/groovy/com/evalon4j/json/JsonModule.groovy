package com.evalon4j.json

import com.evalon4j.Evalon4JConfiguration
import com.evalon4j.frameworks.openapi_annotations.OpenAPIDefinition
import com.evalon4j.frameworks.swagger_annotations.SwaggerDefinition
import com.evalon4j.java.JavaModule

class JsonModule {
    String moduleName

    // RPC Service

    List<JsonService> javaServices = []

    List<JsonStruct> javaModels = []

    // HTTP Service

    List<JsonService> restfulApis = []

    List<JsonStruct> restfulModels = []

    // Swagger / OpenAPI

    SwaggerDefinition swaggerDefinition = null

    OpenAPIDefinition openAPIDefinition = null

    Evalon4JConfiguration cfg = new Evalon4JConfiguration()

    JsonModule() {

    }

    JsonModule(JavaModule javaModule) {
        this.moduleName = javaModule.moduleName

        this.swaggerDefinition = javaModule.swaggerDefinition

        this.openAPIDefinition = javaModule.openAPIDefinition

        this.cfg = javaModule.cfg
    }
}
