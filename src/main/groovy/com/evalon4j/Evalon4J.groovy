package com.evalon4j

import com.alibaba.fastjson.JSON
import com.alibaba.fastjson.serializer.PropertyFilter
import com.alibaba.fastjson.serializer.SerializerFeature
import org.apache.commons.cli.DefaultParser
import org.apache.commons.cli.Options

class Evalon4J {
    static HELP = """
    欢迎使用Evalon4J
    
    项目主页: https://gitee.com/RabiAPI/evalon4j
    
    产品支持QQ群: 244365684
    
    参数说明:
    
    -p {指定Java项目根目录}
    -o {指定导出格式} 允许的格式为 ['markdown', 'asciidoc', 'swagger', 'openapi']
    
    使用示例:
    
    ./evalon4j -p ~/repository/pet-store -o markdown
    """

    static void main(String[] args) {
        if (!args) {
            println HELP
            return
        }

        def options = buildOptions()

        def parser = new DefaultParser()

        def cmd = parser.parse(options, args)

        if (cmd.hasOption("h") || cmd.hasOption("help")) {
            println HELP
            return
        }

        def exportType = cmd.getOptionValue("o", Evalon4JExportType.MARKDOWN)

        if (!Evalon4JExportType.EXPORT_TYPES.contains(exportType)) {
            println "未知的导出类型，允许的导出类型为 ['markdown', 'asciidoc', 'swagger', 'openapi']"

            return
        }

        if (cmd.hasOption("p")) {
            String path = cmd.getOptionValue("p")

            if (Evalon4JExportType.EVALON == exportType) {
                println compile(path)
            } else {
                export(path, exportType)
            }
        } else {
            println "请使用 -p 参数指定Java项目路径"
        }
    }

    static buildOptions() {
        Options options = new Options()

        options.addOption("p", true, "Java项目根目录路径")

        options.addOption("o", true, "指定导出类型")

        options.addOption("c", false, "Evalon4J配置文件路径")

        options.addOption("h", false, HELP)

        options.addOption("help", false, HELP)

        return options
    }

    static compile(String projectPath) {
        def result = new Evalon4JParser().parse(projectPath)

        JSON.DEFAULT_GENERATE_FEATURE |= SerializerFeature.DisableCircularReferenceDetect.getMask();

        def resultStr = JSON.toJSONString(result, propertyFilter, SerializerFeature.PrettyFormat)

        return resultStr
    }

    static export(String projectPath, String exportType) {
        println "xxx"
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


