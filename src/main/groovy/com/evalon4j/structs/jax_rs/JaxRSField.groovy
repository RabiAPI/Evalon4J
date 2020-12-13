package com.evalon4j.structs.jax_rs

import com.evalon4j.structs.jax_rs.params.JaxRSBeanParam
import com.evalon4j.structs.jax_rs.params.JaxRSCookieParam
import com.evalon4j.structs.jax_rs.params.JaxRSFormParam
import com.evalon4j.structs.jax_rs.params.JaxRSHeaderParam
import com.evalon4j.structs.jax_rs.params.JaxRSPathParam
import com.evalon4j.structs.jax_rs.params.JaxRSQueryParam

class JaxRSField {
    JaxRSBeanParam beanParam = null

    JaxRSCookieParam cookieParam = null

    JaxRSFormParam formParam = null

    JaxRSHeaderParam headerParam = null

    JaxRSPathParam pathParam = null

    JaxRSQueryParam queryParam = null
}
