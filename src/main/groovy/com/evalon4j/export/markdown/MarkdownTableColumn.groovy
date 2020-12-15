package com.evalon4j.export.markdown

import com.evalon4j.json.JsonStruct

/**
 * comment
 *
 * @author whitecosm0s_
 */
class MarkdownTableColumn {
    String columnName = ""

    int columnWidth = 0

    boolean indent = true

    Closure callback
}
