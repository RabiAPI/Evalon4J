package com.evalon4j.frameworks.openapi_annotations.servers

import com.evalon4j.frameworks.Annotation

/**
 * Container for repeatable {@link Server} annotation
 *
 * @see Server
 */
class Servers extends Annotation {
    /**
     * An array of Server Objects which is used to provide connectivity information to a target server.
     *
     * @return the servers used for this API
     */
    Server[] value = [];
}
