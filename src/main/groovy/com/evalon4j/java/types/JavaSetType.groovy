package com.evalon4j.java.types

class JavaSetType extends JavaAbstractType {
    String simpleName = "Set"

    JavaAbstractType typeArgument = null

    String getSimpleName() {
        return "Set<${typeArgument.getSimpleName()}>"
    }

    JavaSetType() {

    }

    JavaSetType(JavaAbstractType typeArgument) {
        this.typeArgument = typeArgument
    }
}
