package com.evalon4j.java

import com.evalon4j.frameworks.JaxRSAnnotations
import com.evalon4j.frameworks.OpenAPIAnnotations
import com.evalon4j.frameworks.SpringAnnotations
import com.evalon4j.frameworks.SwaggerAnnotations
import com.evalon4j.java.types.JavaAbstractType

class JavaComponent extends JavadocComponent {
    String simpleName = ""

    String qualifiedName = ""

    String packageName = ""

    String moduleName = ""

    List<String> imports = []

    List<String> typePlaceholders = []

    List<JavaMethod> methods = []

    List<JavaAbstractType> extensions = [] // TODO JavaComponent Or JavaAbstractType

    SpringAnnotations springAnnotations = null

    JaxRSAnnotations jaxRSAnnotations = null

    SwaggerAnnotations swaggerAnnotations = null

    OpenAPIAnnotations openAPIAnnotations = null

    boolean isDeprecated = false
}
