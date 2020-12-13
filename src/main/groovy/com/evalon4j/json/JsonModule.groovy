package com.evalon4j.json

import com.evalon4j.frameworks.openapi.OpenAPIDefinition
import com.evalon4j.frameworks.swagger.SwaggerDefinition
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

    JsonModule() {

    }

    JsonModule(JavaModule javaModule) {
        this.moduleName = javaModule.moduleName

        this.swaggerDefinition = javaModule.swaggerDefinition

        this.openAPIDefinition = javaModule.openAPIDefinition
    }
}
