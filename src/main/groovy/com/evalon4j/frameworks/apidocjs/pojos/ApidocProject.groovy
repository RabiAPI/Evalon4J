package com.evalon4j.frameworks.apidocjs.pojos

/**
 * comment
 *
 * @author whitecosm0s_
 */
class ApidocProject {
    String name = ""

    String version = ""

    String description = ""

    String url = ""

    String sampleUrl = ""

    List<String> order = []

    String defaultVersion = ""

    def header = [
            title  : "",
            content: ""
    ]

    def footer = [
            title  : "",
            content: ""
    ]

    // generator ignore

    // apidoc ignore
}
