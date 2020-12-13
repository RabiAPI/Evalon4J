package com.evalon4j.visitors

import com.evalon4j.java.JavadocComponent
import com.evalon4j.java.KeyValueTag
import com.github.javaparser.ast.nodeTypes.NodeWithJavadoc
import com.github.javaparser.javadoc.JavadocBlockTag

class JavadocHelper {
    static void setJavadoc(JavadocComponent c, NodeWithJavadoc n) {
        c.javadocTitle = getJavadocTitle(n)

        c.javadocContent = getJavadocContent(n)

        c.javadocTags = getJavadocTags(n)
    }

    static String getJavadocTitle(NodeWithJavadoc n) {
        if (!n.getJavadoc().present) {
            return ""
        }

        def javaDoc = n.getJavadoc().get()

        if (!javaDoc.description.toText()) {
            return ""
        }

        return javaDoc.description.toText().readLines().first() // read first line of javadoc
    }

    static String getJavadocContent(NodeWithJavadoc n) {
        if (!n.getJavadoc().present) {
            return ""
        }

        def javaDoc = n.getJavadoc().get()

        if (!javaDoc.description.toText()) {
            return ""
        }

        def lines = javaDoc.description.toText().readLines()

        lines.remove(lines.first())

        return lines.join("\n")
    }

    static String getThrowsComment(NodeWithJavadoc n, String fieldName) {
        if (!n.getJavadoc().present) {
            return ""
        }

        def javadoc = n.getJavadoc().get()

        def doc = javadoc.blockTags.find {
            it.type == JavadocBlockTag.Type.THROWS && it.name.present && it.name.get() == fieldName
        }

        return doc ? doc.getContent().toText() : ""
    }

    static String getParameterComment(NodeWithJavadoc n, String fieldName) {
        if (!n.getJavadoc().present) {
            return ""
        }

        def javadoc = n.javadoc.get()

        def doc = javadoc.blockTags.find {
            it.type == JavadocBlockTag.Type.PARAM && it.name.present && it.name.get() == fieldName
        }

        return doc ? doc.getContent().toText() : ""
    }

    static String getReturnTypeComment(NodeWithJavadoc n) {
        if (!n.getJavadoc().present) {
            return ""
        }

        def javadoc = n.getJavadoc().get()

        def doc = javadoc.blockTags.find {
            it.type == JavadocBlockTag.Type.RETURN
        }

        return doc ? doc.getContent().toText() : ""
    }

    static TAG_WHITE_LIST = [
            "param",
            "return",
            "throws",
    ]

    @SuppressWarnings("GroovyAccessibility")
    static getJavadocTags(NodeWithJavadoc n) {
        def javadocTags = []

        if (!n.getJavadoc().present) {
            return javadocTags
        }

        def javadoc = n.getJavadoc().get()

        if (!javadoc.description.toText()) {
            return javadocTags
        }

        javadoc.blockTags.each { bt ->
            def tag = [:]

            if (TAG_WHITE_LIST.contains(bt.tagName)) {
                return
            }

            if (!bt.content.toText()) { // 跳过没有值的标签
                return
            }

            tag.put(bt.tagName, bt.content.toText())

            javadocTags << new KeyValueTag(tagName: bt.tagName, tagValue: bt.content.toText())
        }

        return javadocTags
    }
}