package com.evalon4j.export.markdown

import com.evalon4j.Evalon4JConfiguration
import com.evalon4j.java.KeyValueTag
import com.evalon4j.json.JsonStruct

import java.time.LocalDate

/**
 * comment
 *
 * @author whitecosm0s_
 */
class Markdown {
    Markdown() {

    }

    private static final String H1 = "# "

    private static final String H2 = "## "

    private static final String H3 = "### "

    private static final String H4 = "#### "

    private static final String H5 = "##### "

    private static final String LIST = " - "

    private static final String LF = "\n"

    private static final String LFLF = "\n\n"


    static h1(String text) {
        return H1 + text + LFLF
    }

    static h2(String text) {
        return H2 + text + LFLF
    }

    static h3(String text) {
        return H3 + text + LFLF
    }

    static h4(String text) {
        return H4 + text + LFLF
    }

    static h5(String text) {
        return H5 + text + LFLF
    }

    static text(String text) {
        return text + LFLF
    }

    static tags(List<KeyValueTag> tags = []) {
        def val = ""

        tags.each { tag ->
            val += LIST + tag.tagName + " : " + tag.tagValue + LF
        }

        val += LF

        return val
    }

    static list(List<String> items) {
        def list = ""

        items.forEach(item -> list += (LIST + item + LF))

        list += LF

        return list
    }

    static FIELD_NAME_CLOSURE = (JsonStruct jsonStruct) -> {
        return jsonStruct.fieldName
    }

    static FIELD_TYPE_NAME_CLOSURE = (JsonStruct jsonStruct) -> {
        return jsonStruct.fieldTypeName
    }

    static FIELD_REQUIRED_CLOSURE = (JsonStruct jsonStruct) -> {
        return jsonStruct.isRequired ? " √ " : " - "
    }

    static FIELD_SUMMARY_CLOSURE = (JsonStruct jsonStruct) -> {
        return jsonStruct.fieldSummary ? jsonStruct.fieldSummary : "/"
    }

    static infoTable(Evalon4JConfiguration cfg) {
        def configs = [
                new MarkdownTableColumn(
                        columnName: "Author",
                        columnWidth: 16,
                        indent: true,
                        callback: () -> {
                            return cfg.author ? cfg.author : "/"
                        }
                ),
                new MarkdownTableColumn(
                        columnName: "Date",
                        columnWidth: 16,
                        callback: () -> {
                            return LocalDate.now().toString()
                        }
                ),
                new MarkdownTableColumn(
                        columnName: "Version",
                        columnWidth: 16,
                        callback: () -> {
                            return cfg.version ? cfg.version : "1.0"
                        }
                ),
                new MarkdownTableColumn(
                        columnName: "Description",
                        columnWidth: 32,
                        callback: () -> {
                            return cfg.summary ? cfg.summary : "/"
                        }
                ),
        ]


        return new MarkdownTableBuilder(configs).build()
    }

    static parametersTable(List<JsonStruct> jsonStructs) {
        def configs = [
                new MarkdownTableColumn(
                        columnName: "Name",
                        columnWidth: MarkdownHelper.getColumnWidth(jsonStructs, "fieldName", 24),
                        indent: true,
                        callback: FIELD_NAME_CLOSURE
                ),
                new MarkdownTableColumn(
                        columnName: "Required",
                        columnWidth: 16,
                        callback: FIELD_REQUIRED_CLOSURE
                ),
                new MarkdownTableColumn(
                        columnName: "Type",
                        columnWidth: MarkdownHelper.getColumnWidth(jsonStructs, "fieldTypeName", 24),
                        callback: FIELD_TYPE_NAME_CLOSURE
                ),
                new MarkdownTableColumn(
                        columnName: "Comment",
                        columnWidth: 32,
                        callback: FIELD_SUMMARY_CLOSURE
                ),
        ]

        return new MarkdownTableBuilder(configs).build(jsonStructs)
    }

    static constraintsTable(List<JsonStruct> jsonStructs) {
        def configs = [
                new MarkdownTableColumn(
                        columnName: "Name",
                        columnWidth: MarkdownHelper.getColumnWidth(jsonStructs, "fieldName", 24),
                        indent: true,
                        callback: FIELD_NAME_CLOSURE,
                ),
                new MarkdownTableColumn(
                        columnName: "Type",
                        columnWidth: 0,
                        callback: FIELD_TYPE_NAME_CLOSURE
                ),
                new MarkdownTableColumn(
                        columnName: "Comment",
                        columnWidth: 32,
                        callback: FIELD_SUMMARY_CLOSURE
                ),
        ]

        return new MarkdownTableBuilder(configs).build(jsonStructs)
    }

    static responseTable(List<JsonStruct> jsonStructs) {
        def configs = [
                new MarkdownTableColumn(
                        columnName: "Name",
                        columnWidth: 0,
                        indent: true,
                        callback: FIELD_NAME_CLOSURE
                ),
                new MarkdownTableColumn(
                        columnName: "Required",
                        columnWidth: 0,
                        callback: FIELD_REQUIRED_CLOSURE
                ),
                new MarkdownTableColumn(
                        columnName: "Type",
                        columnWidth: 0,
                        callback: FIELD_TYPE_NAME_CLOSURE
                ),
                new MarkdownTableColumn(
                        columnName: "Comment",
                        columnWidth: 32,
                        callback: FIELD_SUMMARY_CLOSURE
                ),
        ]

        return new MarkdownTableBuilder(configs).build(jsonStructs)
    }
}
