package com.evalon4j.java.types

class JavaMapType extends JavaAbstractType {
    String simpleName = "Map"

    JavaAbstractType keyTypeArgument

    JavaAbstractType valueTypeArgument

    String getSimpleName() {
        return "Map<${keyTypeArgument.getSimpleName()}, ${valueTypeArgument.simpleName}>"
    }

    JavaMapType() {

    }

    JavaMapType(JavaAbstractType keyTypeArgument, JavaAbstractType valueTypeArgument) {
        this.keyTypeArgument = keyTypeArgument
        this.valueTypeArgument = valueTypeArgument
    }
}
