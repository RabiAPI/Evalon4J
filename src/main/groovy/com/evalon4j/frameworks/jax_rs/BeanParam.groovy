package com.evalon4j.frameworks.jax_rs

import com.evalon4j.frameworks.Annotation

/**
 * The annotation that may be used to inject custom JAX-RS "parameter aggregator" value object
 * into a resource class field, property or resource method parameter.
 * <p>
 * The JAX-RS runtime will instantiate the object and inject all it's fields and properties annotated
 * with either one of the {@code @XxxParam} annotation ({@link PathParam &#64;PathParam},
 * {@link FormParam &#64;FormParam} ...) or the {@link javax.ws.rs.core.Context &#64;Context}
 * annotation. For the POJO classes same instantiation and injection rules apply as in case of instantiation
 * and injection of request-scoped root resource classes.
 * </p>
 * <p>
 * For example:
 * <pre>
 * public class MyBean {
 *   &#64;FormParam("myData")
 *   private String data;
 *
 *   &#64;HeaderParam("myHeader")
 *   private String header;
 *
 *   &#64;PathParam("id")
 *   public void setResourceId(String id) {...}
 *
 *   ...
 * }
 *
 * &#64;Path("myresources")
 * public class MyResources {
 *   &#64;POST
 *   &#64;Path("{id}")
 *   public void post(&#64;BeanParam MyBean myBean) {...}
 *
 *   ...
 * }
 * </pre>
 * </p>
 * <p>
 * Because injection occurs at object creation time, use of this annotation on resource
 * class fields and bean properties is only supported for the default per-request resource
 * class lifecycle. Resource classes using other lifecycles should only use this annotation
 * on resource method parameters.
 * </p>
 *
 * @author Marek Potociar
 * @since 2.0
 */
class BeanParam extends Annotation { // 用于聚合其它类型的参数

}
