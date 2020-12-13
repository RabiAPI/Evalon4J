package com.evalon4j.structs.spring

/**
 * Spring @RequestMapping Annotation
 */
class SpringRequestMapping {
    static String ANNOTATION_NAME = "RequestMapping"

    List<String> values = [] // 路径可以有多个

    List<String> methods = [] // 方法可以有多个

    List<String> params = [] // TODO ignore

    List<String> headers = []

    List<String> consumes = []

    List<String> produces = []
}
