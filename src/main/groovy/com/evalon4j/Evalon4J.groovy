package com.evalon4j

import com.alibaba.fastjson.JSON
import com.alibaba.fastjson.serializer.PropertyFilter
import com.alibaba.fastjson.serializer.SerializerFeature
import com.evalon4j.export.markdown.MarkdownBuilder
import com.evalon4j.frameworks.apidocjs.ApidocParser
import com.evalon4j.json.JsonModule
import org.apache.commons.cli.CommandLine
import org.apache.commons.cli.DefaultParser
import org.apache.commons.cli.Options

class Evalon4J {
    static i18n = new Evalon4Ji18n()

    static void main(String[] args) {
        def cmd = buildCommandLine(args)

        def projectPath = cmd.getOptionValue("p", "")

        def exportType = cmd.getOptionValue("o", Evalon4JExportType.MARKDOWN)

        if (isShowHelp(cmd)) {
            handleShowHelp()

            return
        }

        if (isExportEvalon4JFormat(cmd)) {
            handleExportEvalon4JFormat(projectPath)

            return
        }

        if (isExportApidocJSFormat(cmd)) {
            handleExportApidocJSFormat(projectPath, cmd.getOptionValue("apidoc"))

            return
        }

        if (isExportOtherFormats(cmd)) {
            if (!projectPath) {
                println i18n.noJavaProjectPath()

                return
            }

            handleExportOtherFormats(projectPath, exportType)

            return
        }

        println i18n.unknownExportType()
    }

    static CommandLine buildCommandLine(String[] args) {
        def options = buildOptions()

        def parser = new DefaultParser()

        return parser.parse(options, args)
    }


    static boolean isShowHelp(CommandLine cmd) {
        return cmd.hasOption("h") || cmd.hasOption("help")
    }

    static boolean isExportEvalon4JFormat(CommandLine cmd) {
        def exportType = cmd.getOptionValue("o")

        return exportType == Evalon4JExportType.EVALON
    }

    static boolean isExportApidocJSFormat(CommandLine cmd) {
        return cmd.hasOption("apidoc")
    }

    static boolean isExportOtherFormats(CommandLine cmd) {
        def exportType = cmd.getOptionValue("o")

        return exportType != Evalon4JExportType.EVALON
    }

    static handleShowHelp() {
        println i18n.help()
    }

    static buildOptions() {
        Options options = new Options()

        options.addOption("p", true, "Project Path")

        options.addOption("o", true, "Output Directory")

        options.addOption("c", true, "Configuration")

        options.addOption("h", false, "Help")

        options.addOption("help", false, "Help")

        options.addOption("apidoc", true, "Apidocjs Parsed Payload")

        return options
    }

    static handleExportEvalon4JFormat(String projectPath) {
        def result = new Evalon4JParser().parse(projectPath)

        JSON.DEFAULT_GENERATE_FEATURE |= SerializerFeature.DisableCircularReferenceDetect.getMask()

        def resultStr = JSON.toJSONString(result, propertyFilter, SerializerFeature.PrettyFormat)

        println resultStr
    }

    static handleExportApidocJSFormat(String projectPath, String apidocStr) {
        def result = new ApidocParser(projectPath).parse(apidocStr)

        JSON.DEFAULT_GENERATE_FEATURE |= SerializerFeature.DisableCircularReferenceDetect.getMask()

        def resultStr = JSON.toJSONString(result, propertyFilter, SerializerFeature.PrettyFormat)

        println resultStr
    }

    static handleExportOtherFormats(String projectPath, String exportType) {
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

        jsonResult.data.modules.each { JsonModule module ->
            if (Evalon4JExportType.MARKDOWN == exportType) {
                def markdown = MarkdownBuilder.buildFromJsonModule(module)

                new File(evalon4jDir.absolutePath + "/${module.moduleName}.md").write(markdown)
            }

//            if (Evalon4JExportType.ASCIIDOC == exportType) {
//                def asciidoc = new Markdown(module).toString()
//
//                new File(evalon4jDir.absolutePath + "${module.moduleName}.asciidoc").write(asciidoc)
//            }
//
//            if (Evalon4JExportType.SWAGGER == exportType) {
//                def swagger = new Swagger(module).toString()
//
//                new File(evalon4jDir.absolutePath + "${module.moduleName}_swagger.json").write(swagger)
//            }
//
//            if (Evalon4JExportType.OPENAPI == exportType) {
//                def openapi = new OpenAPI(module).toString()
//
//                new File(evalon4jDir.absolutePath + "${module.moduleName}_openapi.json").write(openapi)
//            }
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


