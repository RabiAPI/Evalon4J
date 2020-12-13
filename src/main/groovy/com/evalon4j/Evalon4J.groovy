package com.evalon4j

import com.alibaba.fastjson.JSON
import com.alibaba.fastjson.serializer.PropertyFilter
import com.alibaba.fastjson.serializer.SerializerFeature
import org.apache.commons.cli.DefaultParser
import org.apache.commons.cli.Options

class Evalon4J {
    static final String EVALON = "evalon"

    static final String MARKDOWN = "evalon"

    static final String ASCIIDOC = "evalon"

    static final String SWAGGER = "evalon"

    static final String OPENAPI = "evalon"

    static void main(String[] args) {
        if (!args) {
            return
        }

        def options = buildOptions()

        def parser = new DefaultParser()

        def cmd = parser.parse(options, args)

        String projectPath = args.first()

        println compile(projectPath)
    }

    static buildOptions() {
        Options options = new Options()

        options.addOption("p", true, "Java Project Directory Absolute Path")

        options.addOption("o", true, "Export Type")

        options.addOption("c", false, "Evalon4J Configuration Json File")

        options.addOption("h", false, "")

        return options
    }

    static compile(String projectPath) {
        def result = new Evalon4JParser().parse(projectPath)

        JSON.DEFAULT_GENERATE_FEATURE |= SerializerFeature.DisableCircularReferenceDetect.getMask();

        def resultStr = JSON.toJSONString(result, propertyFilter, SerializerFeature.PrettyFormat)

        return resultStr
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


