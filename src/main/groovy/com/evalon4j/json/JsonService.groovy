package com.evalon4j.json

import com.evalon4j.java.JavaComponent
import com.evalon4j.java.JavaInterface
import com.evalon4j.java.KeyValueTag

class JsonService {
    String serviceName

    String serviceQualifiedName

    String moduleName = ""

    List<JsonMethod> methods = []

    Map<String, List<JsonMethod>> overloads = [:] // Method Name -> Method List

    boolean isDeprecated = false

    String summary = ""

    String description = ""

    List<KeyValueTag> tags = []

    // Http 相关属性

    String basePath = ""

    boolean isHidden = false // 默认不导出

    boolean isHttpService = false

    boolean isJavaService = false

    JsonService() {

    }

    JsonService(JavaComponent javaComponent) {
        this.serviceName = javaComponent.simpleName

        this.serviceQualifiedName = javaComponent.qualifiedName

        this.moduleName = javaComponent.moduleName

        this.isDeprecated = javaComponent.isDeprecated

        // javadoc

        this.summary = javaComponent.javadocTitle

        this.description = javaComponent.javadocContent

        this.tags = javaComponent.javadocTags
    }
}
