package com.evalon4j.frameworks.validation

class Range extends JavaValidationAnnotation {
    /**
     * @return size the element must be higher or equal to
     */
    int min = 0;

    /**
     * @return size the element must be lower or equal to
     */
    int max = Integer.MAX_VALUE;
}
