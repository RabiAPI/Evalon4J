package com.evalon4j.frameworks.apidocjs

import com.alibaba.fastjson.JSON
import com.evalon4j.Evalon4JResult
import com.evalon4j.frameworks.apidocjs.pojos.ApidocEndpoint
import com.evalon4j.frameworks.apidocjs.pojos.ApidocPayload
import com.evalon4j.frameworks.apidocjs.pojos.ApidocProject
import com.evalon4j.json.JsonProject
import com.evalon4j.frameworks.apidocjs.ApidocTransformer
import com.evalon4j.utils.ExceptionUtils
import com.evalon4j.utils.FileUtils

/**
 * Parse apidoc parsed result to evalon4j format
 *
 * @author whitecosm0s_
 */
class ApidocParser {
    String projectPath

    ApidocParser(String projectPath) {
        this.projectPath = projectPath
    }

    Evalon4JResult parse(String apidocJSPayload) {
        def result = new Evalon4JResult()

        try {
            Object payload = JSON.parse(apidocJSPayload)

            def apidocPayload = new ApidocPayload()

            apidocPayload.project = JSON.parseObject(payload['project'] as String, ApidocProject.class)

            apidocPayload.data = JSON.parseArray(payload['data'] as String, ApidocEndpoint.class)

            def jsonProject = new ApidocTransformer().transform(apidocPayload)

            this.setProjectName(jsonProject)

            result.data = jsonProject

            return result

        } catch (Exception e) {
            e.printStackTrace()

            result.hasError = true

            result.errorMessage = "Resolve Apidoc Error: " + e.getMessage()

            result.errorTraces = ExceptionUtils.filterExceptions(e)

            return result
        }
    }

    def setProjectName(JsonProject jsonProject) {
        String projectName = FileUtils.getProjectName(projectPath)

        jsonProject.projectName = projectName

        jsonProject.projectPath = projectName

        jsonProject.modules.first().moduleName = projectName
    }
}
