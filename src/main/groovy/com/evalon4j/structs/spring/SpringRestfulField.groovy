package com.evalon4j.structs.spring

import com.evalon4j.structs.spring.params.SpringCookieValue
import com.evalon4j.structs.spring.params.SpringModelAttribute
import com.evalon4j.structs.spring.params.SpringPathVariable
import com.evalon4j.structs.spring.params.SpringRequestAttribute
import com.evalon4j.structs.spring.params.SpringRequestBody
import com.evalon4j.structs.spring.params.SpringRequestHeader
import com.evalon4j.structs.spring.params.SpringRequestParam

class SpringRestfulField {
    SpringCookieValue cookieValue = null

    SpringPathVariable pathVariable = null // /foo/{id}

    SpringRequestAttribute requestAttribute = null

    SpringRequestBody requestBody = null // json body @RequestBody

    SpringRequestHeader requestHeader = null

    SpringRequestParam requestParam = null // /foo?id=

    SpringModelAttribute modelAttribute = null
}
