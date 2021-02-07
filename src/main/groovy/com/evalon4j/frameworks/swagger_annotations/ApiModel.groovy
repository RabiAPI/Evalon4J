package com.evalon4j.frameworks.swagger_annotations


import com.evalon4j.frameworks.Annotation

/**
 * Provides additional information about Swagger models.
 * <p>
 * Classes will be introspected automatically as they are used as types in operations,
 * but you may want to manipulate the structure of the models.
 */
class ApiModel extends Annotation {
    /**
     * Provide an alternative name for the model.
     * <p>
     * By default, the class name is used.
     */
    String value = "";

    /**
     * Provide a longer description of the class.
     */
    String description = "";

    /**
     * Provide a superclass for the model to allow describing inheritance.
     */
//    JavaField parent = null; ignore

    /**
     * Supports model inheritance and polymorphism.
     * <p>
     * This is the name of the field used as a discriminator. Based on this field,
     * it would be possible to assert which sub type needs to be used.
     */
    String discriminator = "";

    /**
     * An array of the sub types inheriting from this model.
     */
//    List<JavaField> subTypes = []; // ignore

    /**
     * Specifies a reference to the corresponding type definition, overrides any other metadata specified
     */
    String reference = "";
}
