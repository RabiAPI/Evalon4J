package com.evalon4j.frameworks.spring_cloud

import com.evalon4j.frameworks.Annotation

/**
 * Annotation for interfaces declaring that a REST client with that interface should be created (e.g. for autowiring into another component).
 * If ribbon is available it will be used to load balance the backend requests,
 * and the load balancer can be configured using a @RibbonClient with the same name (i.e. value) as the feign client.
 */
class FeignClient extends Annotation {
    String name = ""

    boolean decode404 = false

    String path = ""

    String url = ""

    String value = ""
}
