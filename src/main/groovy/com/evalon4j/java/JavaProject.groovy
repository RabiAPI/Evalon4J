package com.evalon4j.java

import com.evalon4j.java.types.JavaReferenceType
import com.evalon4j.json.JsonError

class JavaProject {
    String projectName

    String projectPath

    String projectType = "JAVA_PROJECT" // JAVA_PROJECT SOURCE_JAR

    List<JavaModule> modules = []

    List<JavaInterface> interfaces = []

    List<JavaClass> classes = []

    List<JavaReferenceType> references = []

    List<JsonError> errors = []
}
