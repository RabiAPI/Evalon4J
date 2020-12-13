package com.evalon4j.frameworks.spring.parameters

import com.evalon4j.frameworks.Annotation

/**
 * Annotation that binds a method parameter or method return value
 * to a named model attribute, exposed to a web view. Supported
 * for controller classes with {@link RequestMapping @RequestMapping}
 * methods.
 *
 * <p>Can be used to expose command objects to a web view, using
 * specific attribute names, through annotating corresponding
 * parameters of an {@link RequestMapping @RequestMapping} method.
 *
 * <p>Can also be used to expose reference data to a web view
 * through annotating accessor methods in a controller class with
 * {@link RequestMapping @RequestMapping} methods. Such accessor
 * methods are allowed to have any arguments that
 * {@link RequestMapping @RequestMapping} methods support, returning
 * the model attribute value to expose.
 *
 * <p>Note however that reference data and all other model content is
 * not available to web views when request processing results in an
 * {@code Exception} since the exception could be raised at any time
 * making the content of the model unreliable. For this reason
 * {@link ExceptionHandler @ExceptionHandler} methods do not provide
 * access to a {@link Model} argument.
 *
 * @author Juergen Hoeller
 * @author Rossen Stoyanchev
 * @since 2.5
 */
class ModelAttribute extends Annotation {
    /**
     * Alias for {@link #name}.
     */
    String value = ""

    /**
     * The name of the model attribute to bind to.
     * <p>The default model attribute name is inferred from the declared
     * attribute type (i.e. the method parameter type or method return type),
     * based on the non-qualified class name:
     * e.g. "orderAddress" for class "mypackage.OrderAddress",
     * or "orderAddressList" for "List&lt;mypackage.OrderAddress&gt;".
     * @since 4.3
     */
    String name = ""

    /**
     * Allows declaring data binding disabled directly on an {@code @ModelAttribute}
     * method parameter or on the attribute returned from an {@code @ModelAttribute}
     * method, both of which would prevent data binding for that attribute.
     * <p>By default this is set to {@code true} in which case data binding applies.
     * Set this to {@code false} to disable data binding.
     * @since 4.3
     */
    boolean binding = true
}
