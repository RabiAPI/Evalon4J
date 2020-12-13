package com.evalon4j.common

import com.evalon4j.java.types.JavaAbstractType

abstract class Pojo {
    String packageName = ""

    abstract JavaAbstractType build()
}
