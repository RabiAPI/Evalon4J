package com.evalon4j.export.markdown


import com.evalon4j.json.JsonStruct

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

    static parametersTable(List<JsonStruct> jsonStructs) {
        def configs = [
                new MarkdownTableColumn(
                        columnName: "Name",
                        columnWidth: 0,
                        indent: true,
                        callback: (jsonStruct) -> {
                            return jsonStruct.fieldName
                        }
                ),
                new MarkdownTableColumn(
                        columnName: "Required",
                        columnWidth: 0,
                        callback: (jsonStruct) -> {
                            return jsonStruct.fieldName
                        }
                ),
//                new MarkdownTableConfig(
//                        columnName: "Default",
//                        columnWidth: 0,
//                        indent: true,
//                        callback: (jsonStruct) -> {
//                            return jsonStruct.fieldName
//                        }
//                ),
                new MarkdownTableColumn(
                        columnName: "Type",
                        columnWidth: 0,
                        callback: (jsonStruct) -> {
                            return jsonStruct.fieldName
                        }
                ),
                new MarkdownTableColumn(
                        columnName: "Comment",
                        columnWidth: 32,
                        callback: (jsonStruct) -> {
                            return jsonStruct.fieldSummary || "/"
                        }
                ),
        ]

        return new MarkdownTableBuilder(configs).build(jsonStructs)
    }

    static constraintsTable(List<JsonStruct> jsonStructs) {
        def configs = [
                new MarkdownTableColumn(
                        columnName: "Name",
                        columnWidth: 0,
                        indent: true,
                        callback: (jsonStruct) -> {
                            return jsonStruct.fieldName
                        }
                ),
                new MarkdownTableColumn(
                        columnName: "Required",
                        columnWidth: 0,
                        callback: (jsonStruct) -> {
                            return jsonStruct.fieldName
                        }
                ),
//                new MarkdownTableConfig(
//                        columnName: "Default",
//                        columnWidth: 0,
//                        indent: true,
//                        callback: (jsonStruct) -> {
//                            return jsonStruct.fieldName
//                        }
//                ),
                new MarkdownTableColumn(
                        columnName: "Type",
                        columnWidth: 0,
                        callback: (jsonStruct) -> {
                            return jsonStruct.fieldName
                        }
                ),
                new MarkdownTableColumn(
                        columnName: "Comment",
                        columnWidth: 32,
                        callback: (jsonStruct) -> {
                            return jsonStruct.fieldSummary || "/"
                        }
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
                        callback: (jsonStruct) -> {
                            return jsonStruct.fieldName
                        }
                ),
                new MarkdownTableColumn(
                        columnName: "Required",
                        columnWidth: 0,
                        callback: (jsonStruct) -> {
                            return jsonStruct.fieldName
                        }
                ),
//                new MarkdownTableConfig(
//                        columnName: "Default",
//                        columnWidth: 0,
//                        indent: true,
//                        callback: (jsonStruct) -> {
//                            return jsonStruct.fieldName
//                        }
//                ),
                new MarkdownTableColumn(
                        columnName: "Type",
                        columnWidth: 0,
                        callback: (jsonStruct) -> {
                            return jsonStruct.fieldName
                        }
                ),
                new MarkdownTableColumn(
                        columnName: "Comment",
                        columnWidth: 32,
                        callback: (jsonStruct) -> {
                            return jsonStruct.fieldSummary || "/"
                        }
                ),
        ]

        return new MarkdownTableBuilder(configs).build(jsonStructs)
    }


}
