package com.evalon4j.java.types

class JavaListType extends JavaAbstractType {
    String simpleName = "List"

    JavaAbstractType typeArgument

    String getSimpleName() {
        return "List<${typeArgument.simpleName}>"
    }

    JavaListType() {

    }

    JavaListType(JavaAbstractType typeArgument) {
        this.typeArgument = typeArgument
    }
}
