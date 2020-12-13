package com.evalon4j.json

import com.evalon4j.utils.ExceptionUtils

class JsonError {
    String errorMessage = ""

    List<String> stackTraces = []

    JsonError() {

    }

    JsonError(Exception e) {
        this.errorMessage = e.message ? e.message : "No Error Message"

        this.stackTraces = ExceptionUtils.filterExceptions(e)
    }
}
