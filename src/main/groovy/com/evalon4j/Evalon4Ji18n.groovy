package com.evalon4j
/**
 * Simple i18n tool
 *
 * @author whitecosm0s_
 */
class Evalon4Ji18n {
    private String LANG = "en"

    private String EN = "en"

    private String ZH = "zh"

    Evalon4Ji18n() {
        this.LANG = System.getProperty("user.language", "en")

        if (LANG !== ZH) { // Default
            LANG = EN
        }
    }

    def t(String jsonPath) {
        return jsonPath
    }

    def help() {
        if (LANG == EN) {
            return """
            Evalon4J - The Ultimate Java API Documentation Generator
            
            Project On Github: https://github.com/RabiAPI/Evalon4J
    
            Chat On Gitter: https://gitter.im/RabiAPITool/community
    
            Parameters:
    
            -p {Java Project Path}
            -o {Export Format} Supported Type: markdown / swagger / openapi
            
            Example:
    
            ./evalon4j -p ~/repository/pet-store -o markdown
            """
        }

        if (LANG == ZH) {
            return """
            欢迎使用Evalon4J
    
            项目主页: https://gitee.com/RabiAPI/evalon4j
    
            产品支持QQ群: 244365684
    
            参数说明:
    
            -p {指定Java项目根目录}
            -o {指定导出格式} 允许的格式为 markdown / swagger / openapi
    
            使用示例:
    
            ./evalon4j -p ~/repository/pet-store -o markdown
            """
        }
    }

    def unknownExportType() {
        if (LANG == EN) {
            return "Unknown Export Type, Supported Export Type: markdown / swagger / openapi"
        }

        if (LANG == ZH) {
            return "未知的导出类型, 支持的导出类型: markdown / swagger / openapi"
        }
    }

    def noJavaProjectPath() {
        if (LANG == EN) {
            return "Please using `-p` option to specify a java project path."
        }

        if (LANG == ZH) {
            return "请通过 `-p` 参数指定Java项目的路径"
        }
    }

    def complete() {

    }
}
