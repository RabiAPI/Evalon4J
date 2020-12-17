package com.evalon4j.export.markdown

import com.evalon4j.json.JsonStruct

/**
 * comment
 *
 * @author whitecosm0s_
 */
class MarkdownHelper {
    static int getColumnWidth(List<JsonStruct> jsonStructs, String columnName, int defaultWidth = 16) {
        def columnNameCounts = new HashSet<Integer>()

        jsonStructs.each(jsonStruct -> {
            countLongestColumnName(columnName, jsonStruct, 0, columnNameCounts)
        })

        def maxColumnWidth = new ArrayList(columnNameCounts).sort()[0] // 返回最大值

        return maxColumnWidth < defaultWidth ? defaultWidth : maxColumnWidth
    }

    static int getColumnWidth(List<JsonStruct> jsonStructs, List<String> columnNames, int defaultWidth = 16) {
        def columnNameCounts = new HashSet<Integer>()

        jsonStructs.each(jsonStruct -> {
            countLongestColumnName(columnNames, jsonStruct, 0, columnNameCounts)
        })

        def maxColumnWidth = new ArrayList(columnNameCounts).sort()[0] // 返回最大值

        return maxColumnWidth < defaultWidth ? defaultWidth : maxColumnWidth
    }

    static countLongestColumnName(columnName,
                           jsonStruct,
                           layer = 0,
                           counts = new HashSet<Integer>()) {
        counts.add(jsonStruct[columnName].length() + layer * 2)

        if (jsonStruct.children) {
            jsonStruct.children.each(child -> {
                countLongestColumnName(columnName, child, layer + 1, counts)
            })
        }
    }

    static countLongestColumnName(List<String> columnNames,
                                  jsonStruct,
                                  layer = 0,
                                  counts = new HashSet<Integer>()) {
        def length = 0

        columnNames.each { columnName ->
            jsonStruct[columnName] && (length += jsonStruct[columnName].length())
        }

        counts.add(length + layer * 2)

        if (jsonStruct.children) {
            jsonStruct.children.each(child -> {
                countLongestColumnName(columnNames, child, layer + 1, counts)
            })
        }
    }
}
