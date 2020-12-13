package com.evalon4j.java.types

class JavaArrayType extends JavaAbstractType {
    String simpleName = ""

    JavaAbstractType typeArgument

    String getSimpleName() {
        return "${typeArgument.getSimpleName()}[]"
    }
}
