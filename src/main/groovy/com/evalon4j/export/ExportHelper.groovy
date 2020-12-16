package com.evalon4j.export

import com.evalon4j.json.JsonMethod
import com.evalon4j.json.JsonService
import org.jsoup.Jsoup

/**
 * comment
 *
 * @author whitecosm0s_
 */
class ExportHelper {
    static String getServiceSummary(JsonService jsonService) {
        return jsonService.summary ? Jsoup.parse(jsonService.summary).text() : jsonService.serviceName
    }

    static String getServiceDescription(JsonService jsonService) {
        return jsonService.description ? Jsoup.parse(jsonService.description).text() : "/"
    }

    static String getMethodSummary(JsonMethod jsonMethod) {
        return jsonMethod.summary ? Jsoup.parse(jsonMethod.summary).text() : jsonMethod.methodName
    }

    static String getMethodDescription(JsonMethod jsonMethod) {
        return jsonMethod.description ? Jsoup.parse(jsonMethod.description).text() : "/"
    }
}
