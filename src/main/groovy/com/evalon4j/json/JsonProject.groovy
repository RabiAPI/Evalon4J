package com.evalon4j.json

import com.evalon4j.frameworks.apidocjs.pojos.ApidocProject
import com.evalon4j.java.JavaProject

class JsonProject {
    String projectName

    String projectPath

    String projectType = "" // JAVA_PROJECT SOURCE_JAR SWAGGER OPENAPI RAML etc. APIDOC

    String uuid = "" // 每次生成的UUID，用于判断示例项目是否需要更新

    List<JsonModule> modules = []

    List<JsonError> errors = [] // 解析器内部异常

    ApidocProject apidocProject = null

    JsonProject() {

    }

    JsonProject(JavaProject javaProject) {
        this.projectName = javaProject.projectName

        this.projectPath = javaProject.projectPath

        this.projectType = javaProject.projectType

        this.uuid = UUID.randomUUID().toString()
    }
}
