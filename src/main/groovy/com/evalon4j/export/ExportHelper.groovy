package com.evalon4j.export

import com.evalon4j.json.JsonMethod
import com.evalon4j.json.JsonService

/**
 * comment
 *
 * @author whitecosm0s_
 */
class ExportHelper {
    static String getServiceSummary(JsonService jsonService) {
        return jsonService.summary ? jsonService.summary : jsonService.serviceName
    }

    static String getServiceDescription(JsonService jsonService) {
        return jsonService.description ? jsonService.description : "/"
    }

    static String getMethodSummary(JsonMethod jsonMethod) {
        return jsonMethod.summary ? jsonMethod.summary : jsonMethod.methodName
    }

    static String getMethodDescription(JsonMethod jsonMethod) {
        return jsonMethod.description ? jsonMethod.description : "/"
    }
}
