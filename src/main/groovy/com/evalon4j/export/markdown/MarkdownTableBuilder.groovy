package com.evalon4j.export.markdown

import com.evalon4j.json.JsonStruct

/**
 * Used For Creating Markdown Table
 *
 * @author whitecosm0s_
 */
class MarkdownTableBuilder {
    private static String TABLE_DIVIDER = "|"

    private static String UNDER_LINE = "-"

    private static String LF = "\n"

    private static String FIELD_CHILDREN_SYMBOL = "└ "

    private static String FIELD_DIVIDER_SYMBOL = "··"

    List<MarkdownTableColumn> configs = []

    MarkdownTableBuilder() {

    }

    MarkdownTableBuilder(List<MarkdownTableColumn> configs) {
        this.configs = configs
    }

    def build(List<JsonStruct> jsonStructs) {
        def table = ""

        table += buildTableTitle(this.configs)

        table += buildTableDivider(this.configs)

        table += buildTableRows(this.configs, jsonStructs, 0)

        table += "\n"

        return table
    }

    def buildTableTitle(List<MarkdownTableColumn> configs) {
        def title = ""

        configs.each { config ->
            def columnName = config.columnName

            def columnWidth = config.columnWidth

            title += TABLE_DIVIDER + space(1) + columnName + space(columnWidth - columnName.size())
        }

        title += LF

        return title
    }

    def buildTableDivider(List<MarkdownTableColumn> configs) {
        def divider = ""

        configs.each(config -> {
            def columnWidth = config.columnWidth

            divider += TABLE_DIVIDER + space(1) + underline(columnWidth)
        })

        divider += ENTER

        return divider
    }

    def buildTableRows(List<MarkdownTableColumn> configs, List<JsonStruct> jsonStructs, layer) {
        def rows = ""

        jsonStructs.each(jsonStruct -> {
            rows += buildTableRow(configs, jsonStruct, layer)

            if (!_.isEmpty(jsonStruct.children)) {
                rows += buildTableRows(configs, jsonStruct.children, layer + 1)
            }
        })

        return rows
    }

    def buildTableRow(List<MarkdownTableColumn> configs, JsonStruct jsonStruct, layer) {
        def row = ""

        configs.each(config -> {
            def columnWidth = config.columnWidth

            def columnValue = config.callback(jsonStruct)

            if (config.indent && layer > 0) {
                columnValue = placeholder(layer) + FIELD_CHILDREN_SYMBOL + columnValue
            }

            row += TABLE_DIVIDER + space(1) + columnValue + space(columnWidth - columnValue.length)
        })

        row += LF

        return row
    }

    def space(int length) {
        def s = " "

        for (def i = 0; i < length; i++) {
            s += " "
        }

        return s
    }

    def underline(int length) {
        def s = UNDER_LINE

        for (def i = 0; i < length; i++) {
            s += UNDER_LINE
        }

        return s
    }

    def placeholder(int length) {
        def s = ""

        if (length === 1) {
            return s
        }

        for (def i = 0; i < length; i++) {
            s += FIELD_DIVIDER_SYMBOL
        }

        return s
    }
}
