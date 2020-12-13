package com.evalon4j.frameworks.swagger


import com.evalon4j.frameworks.Annotation


/**
 * Annotation used to construct ApiKey Auth security definition.
 */
class ApiKeyAuthDefinition extends Annotation {

    /**
     * Key used to refer to this security definition
     *
     * @return key used to refer to this security definition
     */
    String key = ""

    /**
     * A short description for security scheme.
     * @return a short description for security scheme.
     */
    String description = ""

    /**
     * The location of the API key. Valid values are "query" or "header"
     *
     * @return
     */
    ApiKeyLocation in_;

    /**
     * The name of the header or query parameter to be used.
     * @return the name of the header or query parameter to be used.
     */
    String name = ""

    enum ApiKeyLocation {
        HEADER, QUERY;

        private static Map<String, ApiKeyLocation> names = new HashMap<String, ApiKeyLocation>()

        static ApiKeyLocation forValue(String value) {
            return names.get(value.toLowerCase())
        }

        String toValue() {
            for (Map.Entry<String, ApiKeyLocation> entry : names.entrySet()) {
                if (entry.getValue() == this) {
                    return entry.getKey()
                }
            }

            return null // or fail
        }

        static {
            names.put("header", HEADER)
            names.put("query", QUERY)
        }
    }
}
