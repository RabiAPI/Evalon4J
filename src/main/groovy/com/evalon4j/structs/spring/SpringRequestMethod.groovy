package com.evalon4j.structs.spring

class SpringRequestMethod {
    static GET_MAPPING = "GetMapping"

    static POST_MAPPING = "PostMapping"

    static PUT_MAPPING = "PutMapping"

    static PATCH_MAPPING = "PatchMapping"

    static DELETE_MAPPING = "DeleteMapping"

    static GET = "GET"

    static HEAD = "HEAD"

    static POST = "POST"

    static PUT = "PUT"

    static DELETE = "DELETE"

    static PATCH = "PATCH"

    static OPTIONS = "OPTIONS"

    static TRACE = "TRACE"

    static MAPPING = [
            GetMapping   : GET,
            PostMapping  : POST,
            PutMapping   : PUT,
            PatchMapping : PATCH,
            DeleteMapping: DELETE,
    ]
}
