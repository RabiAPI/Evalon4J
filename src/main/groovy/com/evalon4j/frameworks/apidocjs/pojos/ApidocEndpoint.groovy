package com.evalon4j.frameworks.apidocjs.pojos

/**
 * comment
 *
 * @author whitecosm0s_
 */
class ApidocEndpoint {
    // Api Group

    String group

    String groupTitle

    String groupDescription

    ApidocDeprecated deprecated

    // Api

    String name

    String title

    String description

    String version

    String type // http method

    String url // http path

    String filename

    List<ApidocPermission> permission = []

    ApidocFields parameter = null

    ApidocFields success = null

    ApidocFields error = null

    ApidocFields header = null

    List<ApidocExample> examples = []

    List<ApidocSampleRequest> sampleRequest = []
}
