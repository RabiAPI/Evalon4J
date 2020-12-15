package com.evalon4j

import com.alibaba.fastjson.JSON
import com.alibaba.fastjson.serializer.PropertyFilter
import com.alibaba.fastjson.serializer.SerializerFeature
import com.evalon4j.export.markdown.Markdown
import com.evalon4j.export.openapi.OpenAPI
import com.evalon4j.export.swagger.Swagger
import org.apache.commons.cli.DefaultParser
import org.apache.commons.cli.Options

class Evalon4J {
    static i18n = new Evalon4Ji18n()

    static void main(String[] args) {
        if (!args) {
            println i18n.help()

            return
        }

        if (args.size() == 1) { // Only A Project Path Argument, this is for RabiAPI
            println compile(args[0])

            return
        }

        def options = buildOptions()

        def parser = new DefaultParser()

        def cmd = parser.parse(options, args)

        if (cmd.hasOption("h") || cmd.hasOption("help")) {
            println i18n.help()

            return
        }

        def exportType = cmd.getOptionValue("o", Evalon4JExportType.MARKDOWN)

        if (!Evalon4JExportType.EXPORT_TYPES.contains(exportType)) {
            println i18n.unknownExportType()

            return
        }

        if (cmd.hasOption("p")) {
            String path = cmd.getOptionValue("p")

            export(path, exportType)
        } else {
            println i18n.noJavaProjectPath()
        }
    }

    static buildOptions() {
        Options options = new Options()

        options.addOption("p", true, "")

        options.addOption("o", true, "")

        options.addOption("c", false, "")

        options.addOption("h", false, "")

        options.addOption("help", false, "")

        return options
    }

    static compile(String projectPath) {
        def result = new Evalon4JParser().parse(projectPath)

        JSON.DEFAULT_GENERATE_FEATURE |= SerializerFeature.DisableCircularReferenceDetect.getMask();

        def resultStr = JSON.toJSONString(result, propertyFilter, SerializerFeature.PrettyFormat)

        return resultStr
    }

    static export(String projectPath, String exportType) {
        def jsonResult = new Evalon4JParser().parse(projectPath)

        if (jsonResult.hasError) {
            // TODO handle error
            return
        }

        def evalon4jDir = new File(projectPath + "/evalon4j")

        if (evalon4jDir.exists()) { // Override the output
            evalon4jDir.deleteDir()
        }

        evalon4jDir.mkdir()

        jsonResult.data.modules.each { module ->
            if (Evalon4JExportType.MARKDOWN == exportType) {
                def markdown = new Markdown(module).toString()

                new File(evalon4jDir.absolutePath + "${module.moduleName}.md").write(markdown)
            }

            if (Evalon4JExportType.ASCIIDOC == exportType) {
                def asciidoc = new Markdown(module).toString()

                new File(evalon4jDir.absolutePath + "${module.moduleName}.asciidoc").write(asciidoc)
            }

            if (Evalon4JExportType.SWAGGER == exportType) {
                def swagger = new Swagger(module).toString()

                new File(evalon4jDir.absolutePath + "${module.moduleName}_swagger.json").write(swagger)
            }

            if (Evalon4JExportType.OPENAPI == exportType) {
                def openapi = new OpenAPI(module).toString()

                new File(evalon4jDir.absolutePath + "${module.moduleName}_openapi.json").write(openapi)
            }
        }

        println i18n.complete()
    }

    static JSON_PROPERTIES_WHITE_LIST = [
            "parameters",
            "response",
            "exceptions",
            "isRequired"
    ]

    static PropertyFilter propertyFilter = new PropertyFilter() { // 过滤为false的字段
        @Override
        boolean apply(Object object, String name, Object value) {
            if (name in JSON_PROPERTIES_WHITE_LIST) {
                return true
            }

            if (value instanceof Boolean && !value) {
                return false
            }

            if (value instanceof List && value.isEmpty()) {
                return false
            }

            if (value instanceof String && value.isEmpty()) {
                return false
            }

            return true
        }
    }
}


