package com.evalon4j.utils

class ExceptionUtils {
    static List<String> filterExceptions(Exception e) {
        List<String> messages = []

        messages << e.toString()

        messages.addAll(e.stackTrace.collect { st ->
            st.toString()
        }.findAll {
            it.startsWith("com.evalon4j")
        })

        return messages
    }

    static List<String> filterExceptions(Error e) {
        List<String> messages = []

        messages << e.toString()

        messages.addAll(e.stackTrace.collect { st ->
            st.toString()
        }.findAll {
            it.startsWith("com.evalon4j")
        })

        return messages
    }
}
