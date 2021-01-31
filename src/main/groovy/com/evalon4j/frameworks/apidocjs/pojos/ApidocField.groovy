package com.evalon4j.frameworks.apidocjs.pojos

/**
 * A single parameter field.
 *
 * @author whitecosm0s_
 */
class ApidocField {
    String group

    String field

    String type

    String size = null

    List<String> allowedValues = []

    boolean optional = false

    String defaultValue

    String description
}
