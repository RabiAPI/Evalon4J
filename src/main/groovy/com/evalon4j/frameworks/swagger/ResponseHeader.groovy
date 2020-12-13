package com.evalon4j.frameworks.swagger

import com.alibaba.fastjson.annotation.JSONField
import com.evalon4j.frameworks.Annotation
import com.evalon4j.java.types.JavaAbstractType

/**
 * Represents a header that can be provided as part of the response.
 */
class ResponseHeader extends Annotation {
    /**
     * Header's name.
     */
    String name = "";

    /**
     * Long description of the response header.
     */
    String description = "";
}
