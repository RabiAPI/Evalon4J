package com.evalon4j.frameworks.swagger

/**
 * comment
 *
 * @author whitecosm0s_
 */
class Response extends Reference {
    String description

    Schema schema

    Map<String, Header> headers

    Map<String, Object> examples // {MIME TYPE} -> ANY
}
