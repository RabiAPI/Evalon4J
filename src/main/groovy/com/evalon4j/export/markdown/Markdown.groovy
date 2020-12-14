package com.evalon4j.export.markdown

import com.evalon4j.json.JsonModule

/**
 * comment
 *
 * @author whitecosm0s_
 */
class Markdown {
    Markdown() {

    }

    Markdown(JsonModule jsonModule) {

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
}
