package com.evalon4j.frameworks.openapi_annotations

import com.evalon4j.frameworks.Annotation
import com.evalon4j.frameworks.openapi_annotations.extensions.Extension
import com.evalon4j.frameworks.openapi_annotations.info.Info
import com.evalon4j.frameworks.openapi_annotations.servers.Server
import com.evalon4j.frameworks.openapi_annotations.tags.Tag

class OpenAPIDefinition extends Annotation {
    Info info

    // security

    Extension[] extensions

    ExternalDocumentation externalDocs

    Server[] servers

    List<Tag> tags = []
}
