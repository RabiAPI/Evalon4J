package com.evalon4j

class Evalon4JResult {
    boolean hasError = false

    String errorCode = ""

    String errorMessage = ""

    List<String> errorTraces = []

    Object data = null
}
