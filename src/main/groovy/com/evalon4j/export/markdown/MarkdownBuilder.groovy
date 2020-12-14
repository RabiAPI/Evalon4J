package com.evalon4j.export.markdown

import com.evalon4j.Evalon4JConfiguration
import com.evalon4j.export.ExportHelper
import com.evalon4j.json.JsonModule

/**
 * comment
 *
 * @author whitecosm0s_
 */
class MarkdownBuilder {
    static String buildFromJsonModule(JsonModule jsonModule, Evalon4JConfiguration cfg) {
        def md = ""

        md += Markdown.h1(jsonModule.moduleName)

        md += Markdown.text("description")

        if (jsonModule.restfulApis) {
            md += Markdown.h2("HTTP API")
        }

        jsonModule.restfulApis.each {jsonService ->
//            if (ExportHelper.isUnchecked(jsonService.checkedStatus)) {
//                continue
//            }

            md += Markdown.h3(ExportHelper.getServiceTitle(jsonService))

            md += Markdown.text(ExportHelper.getServiceSubtitle(jsonService))

            jsonService.methods.each { jsonMethod ->
//                if (ExportHelper.isUnchecked(jsonMethod.checkedStatus)) {
//                    continue
//                }

                md += Markdown.h4(ExportHelper.getMethodTitle(jsonMethod))

                md += Markdown.text(ExportHelper.getMethodSubtitle(jsonMethod))

                md += Markdown.tags(jsonMethod.tags)

                md += Markdown.h5(i18n.t("MarkdownBuilder.requestMethod"))

                md += Markdown.text(jsonMethod.requestMethod)

                md += Markdown.h5(i18n.t("MarkdownBuilder.requestPath"))

                md += Markdown.text(jsonMethod.fullRequestPath)

                if (!ExportHelper.isEmpty(jsonMethodContent.consumes)) {
                    md += Markdown.h5(i18n.t("MarkdownBuilder.consumes"))

                    md += Markdown.list(jsonMethodContent.consumes)
                }

                if (!ExportHelper.isEmpty(jsonMethodContent.headers)) {
                    md += Markdown.h5(i18n.t("MarkdownBuilder.requestHeaders"))

                    md += Markdown.parametersTable(jsonMethodContent.headers)
                }

                if (!ExportHelper.isEmpty(jsonMethodContent.cookies)) {
                    md += Markdown.h5(i18n.t("MarkdownBuilder.requestCookies"))

                    md += Markdown.parametersTable(jsonMethodContent.cookies)
                }

                md += Markdown.h5(i18n.t("MarkdownBuilder.parameters"))

                if (ExportHelper.isEmpty(jsonMethodContent.parameters)) {
                    md += Markdown.text(i18n.t("MarkdownBuilder.noParameter"))
                } else {
                    md += Markdown.parametersTable(jsonMethodContent.parameters)
                }

                if (!ExportHelper.isEmpty(jsonMethodContent.parameterConstraints)) {
                    md += Markdown.h5(i18n.t("MarkdownBuilder.parameterConstraints"))

                    md += Markdown.parameterConstraintsTable(jsonMethodContent.parameterConstraints)
                }

                if (!ExportHelper.isEmpty(jsonMethodContent.produces)) {
                    md += Markdown.h5(i18n.t("MarkdownBuilder.produces"))

                    md += Markdown.list(jsonMethodContent.produces)
                }

                md += Markdown.h5(i18n.t("MarkdownBuilder.response"))

                if (ExportHelper.isEmpty(jsonMethodContent.responses)) {
                    md += Markdown.text(i18n.t("MarkdownBuilder.noResponse"))
                } else {
                    md += Markdown.responsesTable(jsonMethodContent.responses)
                }

                if (!ExportHelper.isEmpty(jsonMethodContent.responseHeaders)) {
                    md += Markdown.h5(i18n.t("MarkdownBuilder.responseHeaders"))

                    md += Markdown.responsesTable(jsonMethodContent.responseHeaders)
                }
            }
        }

//        if (!ExportHelper.isEmpty(jsonModule.restfulApis)) {
//            Markdown.h2(md, "JAVA API")
//        }

        jsonModule.javaServices.each {jsonService ->
//            if (ExportHelper.isUnchecked(jsonService.checkedStatus)) {
//                continue
//            }

            md += Markdown.h3(ExportHelper.getServiceSummary(jsonService))

            md += Markdown.text(ExportHelper.getServiceDescription(jsonService))

            jsonService.methods.each { jsonMethod ->
                md += Markdown.h4(ExportHelper.getMethodTitle(jsonMethod))

                md += Markdown.text(ExportHelper.getMethodSubtitle(jsonMethod))

                md += Markdown.tags(jsonMethod.tags)

                md += Markdown.h5(i18n.t("MarkdownBuilder.parameters"))

                if (ExportHelper.isEmpty(jsonMethodContent.parameters)) {
                    md += Markdown.text(i18n.t("MarkdownBuilder.noParameter"))
                } else {
                    md += Markdown.parametersTable(jsonMethodContent.parameters)
                }

                if (!ExportHelper.isEmpty(jsonMethodContent.parameterConstraints)) {
                    md += Markdown.h5(i18n.t("MarkdownBuilder.parameterConstraints"))

                    md += Markdown.parameterConstraintsTable(jsonMethodContent.parameterConstraints)
                }

                md += Markdown.h5(i18n.t("MarkdownBuilder.responses"))

                if (ExportHelper.isEmpty(jsonMethodContent.responses)) {
                    md += Markdown.text(i18n.t("MarkdownBuilder.noResponse"))
                } else {
                    md += Markdown.responsesTable(jsonMethodContent.responses)
                }

                if (!ExportHelper.isEmpty(jsonMethodContent.exceptions)) {
                    md += Markdown.h5(i18n.t("MarkdownBuilder.exceptions"))

                    md += Markdown.responsesTable(jsonMethodContent.exceptions)
                }
            }
        }

        return md
    }
}
