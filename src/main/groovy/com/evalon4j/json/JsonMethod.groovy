package com.evalon4j.json

import com.evalon4j.frameworks.JaxRSAnnotations
import com.evalon4j.frameworks.OpenAPIAnnotations
import com.evalon4j.frameworks.SpringAnnotations
import com.evalon4j.frameworks.SwaggerAnnotations
import com.evalon4j.java.JavaMethod
import com.evalon4j.java.KeyValueTag

class JsonMethod {
    String serviceName

    String serviceQualifiedName

    String serviceSummary = ""

    String serviceDescription = ""

    String methodName = ""

    boolean isDeprecated = false

    boolean isJavaService = false

    boolean isHttpService = false

    String summary = ""

    String description = ""

    String prefix = ""

    List<KeyValueTag> tags = []

    // Common

    List<JsonStruct> parameters = []

    List<JsonStruct> parameterConstraints = []

    List<JsonStruct> responses = []

    // Java Service

    List<JsonStruct> exceptions = []

    // Http Service

    String requestMethod = ""

    String requestPath = ""

    String basePath = ""

    String fullRequestPath = "" // basePath + requestPath

    List<JsonStruct> headers = []

    List<JsonStruct> cookies = []

    List<String> consumes = []

    List<String> produces = []

    List<JsonStruct> responseHeaders = [] //

    SwaggerAnnotations swaggerAnnotations = null

    OpenAPIAnnotations openAPIAnnotations = null

    SpringAnnotations springAnnotations = null

    JaxRSAnnotations jaxRSAnnotations = null

    JsonMethod() {

    }

    JsonMethod(JavaMethod javaMethod) {
        this.serviceName = javaMethod.serviceName

        this.serviceQualifiedName = javaMethod.serviceQualifiedName

        this.serviceSummary = javaMethod.serviceJavadocTitle

        this.serviceDescription = javaMethod.serviceJavadocContent

        this.methodName = javaMethod.methodName

        this.isDeprecated = javaMethod.isDeprecated

        this.summary = javaMethod.javadocTitle

        this.description = javaMethod.javadocContent

        this.tags = javaMethod.javadocTags
    }
}
