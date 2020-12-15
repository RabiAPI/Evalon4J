package com.evalon4j.export.markdown

import com.evalon4j.json.JsonStruct

/**
 * comment
 *
 * @author whitecosm0s_
 */
class MarkdownHelper {
    def getColumnWidth(List<JsonStruct> jsonStructs, String columnName, int defaultWidth = 16) {
//        def columnNameCounts = new HashSet<Integer>()
//
//        jsonStructs.each(jsonStruct -> {
//            countLongestColumnName(columnName, jsonStruct, 0, columnNameCounts)
//        })
//
//        def maxColumnWidth = Array.from(columnNameCounts).sort(compare)[0] // 返回最大值
//
//        return maxColumnWidth < defaultWidth ? defaultWidth : maxColumnWidth
    }

//    def countLongestColumnName(columnName,
//                           jsonStruct,
//                           layer = 0,
//                           counts = new Set([])) {
//        counts.add(jsonStruct[columnName].length + layer * 2)
//
//        if (!_.isEmpty(jsonStruct.children)) {
//            jsonStruct.children.each(child -> {
//                countLongestColumnName(columnName, child, layer + 1, counts)
//            })
//        }
//    }
}
