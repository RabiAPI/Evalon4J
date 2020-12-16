package com.evalon4j

import com.evalon4j.json.JsonProject

class Evalon4JResult {
    boolean hasError = false

    String errorCode = ""

    String errorMessage = ""

    List<String> errorTraces = []

    JsonProject data = null
}
