package com.evalon4j.java

import com.evalon4j.frameworks.JaxRSAnnotations
import com.evalon4j.frameworks.OpenAPIAnnotations
import com.evalon4j.frameworks.SpringAnnotations
import com.evalon4j.frameworks.SwaggerAnnotations
import com.evalon4j.frameworks.ValidationAnnotations
import com.evalon4j.java.types.JavaAbstractType

class JavaMethod extends JavadocComponent {
    String serviceName

    String serviceQualifiedName

    String serviceJavadocTitle

    String serviceJavadocContent

    String methodName

    List<JavaField> parameters = []

    JavaAbstractType response = null

    List<JavaField> exceptions = []

    boolean isDeprecated = false

    SpringAnnotations springAnnotations = null

    JaxRSAnnotations jaxRSAnnotations = null

    SwaggerAnnotations swaggerAnnotations = null

    OpenAPIAnnotations openAPIAnnotations = null

    JavaMethod() {

    }

    JavaMethod(JavaComponent javaComponent) {
        this.serviceName = javaComponent.simpleName

        this.serviceQualifiedName = javaComponent.qualifiedName

        this.serviceJavadocTitle = javaComponent.javadocTitle

        this.serviceJavadocContent = javaComponent.javadocContent
    }
}