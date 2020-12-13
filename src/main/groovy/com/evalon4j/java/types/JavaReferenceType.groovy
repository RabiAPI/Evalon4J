package com.evalon4j.java.types

import com.evalon4j.java.JavaField
import com.evalon4j.java.JavaMethod

/**
 * 包括 class 和 enum，不包括 interface
 */
class JavaReferenceType extends JavaAbstractType {
    //存在复数个星号引用的可能性
    List<String> possibleQualifiedNames = []

    String qualifiedName = ""

    String packageName = ""

    String moduleName = ""

    List<String> imports = []

    List<String> typePlaceholders = [] // 泛型占位符，S, T

    List<JavaAbstractType> extensions = []

    List<JavaField> staticFields = []

    List<JavaField> fields = []

    List<JavaMethod> methods = []

    boolean isEnum = false // class or enum

    List<JavaEnumValue> enumValues = []

    boolean isRecursive = false

    boolean isDeprecated = false

    boolean notExists = false
}
