package com.evalon4j.json

class JsonConstraint {
    String type // string array

    String multipleOf // 数字倍率

    String minimum

    String exclusiveMinimum

    String maximum

    String exclusiveMaximum

    String minLength

    String maxLength

    String pattern = ""

    String minItems

    String maxItems

    boolean uniqueItems = false

    String minProperties

    String maxProperties

    boolean required = false

    List<String> enum_ = []

    // number

    boolean positive = false

    boolean positiveOrZero = false

    boolean negative = false

    boolean negativeOrZero = false

    // date-time

    boolean past = false

    boolean pastOrPresent = false

    boolean future = false

    boolean futureOrPresent = false
}
