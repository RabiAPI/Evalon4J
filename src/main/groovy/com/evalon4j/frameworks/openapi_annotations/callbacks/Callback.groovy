package com.evalon4j.frameworks.openapi_annotations.callbacks

import com.evalon4j.frameworks.Annotation

class Callback extends Annotation {
    /**
     * The friendly name used to refer to this callback
     *
     * @return the name of the callback
     **/
    String name= "";

    /**
     * An absolute URL which defines the destination which will be called with the supplied operation definition.
     *
     * @return the callback URL
     */
    String callbackUrlExpression= "";

    /**
     * The array of operations that will be called out-of band
     *
     * @return the callback operations
     **/
//    Operation[] operation= {};

    /**
     * The list of optional extensions
     *
     * @return an optional array of extensions
     */
//    Extension[] extensions= {};

    /**
     * A reference to a Callback defined in components Callbacks.
     *
     * @since 2.0.3
     * @return the reference
     **/
    String ref= "";
}
