package com.evalon4j.frameworks.openapi

import com.evalon4j.frameworks.Annotation
import com.evalon4j.frameworks.openapi.extensions.Extension
import com.evalon4j.frameworks.openapi.info.Info
import com.evalon4j.frameworks.openapi.servers.Server
import com.evalon4j.frameworks.openapi.tags.Tag

class OpenAPIDefinition extends Annotation {
    Info info

    // security

    Extension[] extensions

    ExternalDocumentation externalDocs

    Server[] servers

    List<Tag> tags = []
}
