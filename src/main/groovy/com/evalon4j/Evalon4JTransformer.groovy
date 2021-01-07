package com.evalon4j

import com.evalon4j.http.RestfulApiBuilder
import com.evalon4j.java.*
import com.evalon4j.json.*
import com.evalon4j.transformer.JsonConstraintTransformer
import com.evalon4j.transformer.JsonStructTransformer
import org.jsoup.Jsoup

class Evalon4JTransformer {
    static JsonProject transform(JavaProject javaProject) {
        def jsonProject = new JsonProject(javaProject)

        List<JsonService> jsonServices = transformJavaServices(javaProject, jsonProject)

        List<JsonStruct> jsonModels = extractJsonStructs(jsonServices)

        List<JsonService> restfulApis = transformRestfulApiGroups(javaProject, jsonProject)

        List<JsonStruct> restfulApiModels = extractJsonStructs(restfulApis)

        if (isSingleModule(javaProject)) {
            def jsonModule = new JsonModule(javaProject.modules.first())

            jsonModule.javaServices = jsonServices

            jsonModule.restfulApis = restfulApis

            jsonModule.javaModels = jsonModels.unique {
                it.qualifiedName
            }.sort {
                it.fieldTypeName
            }

            jsonModule.restfulModels = restfulApiModels.unique {
                it.qualifiedName
            }.sort {
                it.fieldTypeName
            }

            jsonProject.modules << jsonModule
        } else {
            javaProject.modules.each { javaModule ->
                def jsonModule = new JsonModule(javaModule)

                jsonModule.javaServices = jsonServices.findAll {
                    it.moduleName == javaModule.moduleName
                }

                jsonModule.restfulApis = restfulApis.findAll {
                    it.moduleName == javaModule.moduleName
                }

                jsonModule.javaModels = jsonModels.findAll {
                    it.moduleName == javaModule.moduleName
                }.unique {
                    it.qualifiedName
                }.sort {
                    it.fieldTypeName
                }

                jsonModule.restfulModels = restfulApiModels.findAll {
                    it.moduleName == javaModule.moduleName
                }.unique {
                    it.qualifiedName
                }.sort {
                    it.fieldTypeName
                }

                jsonProject.modules << jsonModule
            }
        }

        return jsonProject
    }

    static boolean isSingleModule(JavaProject javaProject) {
        return javaProject.modules.size() == 1
    }

    static List<JsonService> transformRestfulApiGroups(JavaProject javaProject, JsonProject jsonProject) {
        def jsonServices = []

        javaProject.classes.each { javaClass ->
            try {
                def group = RestfulApiBuilder.buildRestfulApiGroups(javaClass, jsonProject)

                group && jsonServices << group
            } catch (Exception e) {
                jsonProject.errors << new JsonError(e)
            }
        }

        javaProject.interfaces.each { javaInterface ->
            try {
                def group = RestfulApiBuilder.buildRestfulApiGroups(javaInterface, jsonProject)

                group && jsonServices << group
            } catch (Exception e) {
                jsonProject.errors << new JsonError(e)
            }
        }

        filterHtmlInSummary(jsonServices)

        return jsonServices
    }

    static filterHtmlInSummary(List<JsonService> jsonServices) {
        jsonServices.each { jsonService ->
            jsonService.summary && (jsonService.summary = Jsoup.parse(jsonService.summary).text())

            jsonService.methods.each { jsonMethod ->
                jsonMethod.summary && (jsonMethod.summary = Jsoup.parse(jsonMethod.summary).text())
            }
        }
    }

    static List<JsonService> transformJavaServices(JavaProject javaProject, JsonProject jsonProject) {
        List<JsonService> jsonServices = []

        javaProject.interfaces.each { JavaInterface javaService ->
            if (!javaService.methods) {
                return
            }

            try {
                def jsonService = new JsonService(javaService)

                jsonService.isJavaService = true

                javaService.methods.each { javaMethod ->
                    if (isRestfulApi(javaService, javaMethod)) {
                        return
                    }

                    try {
                        def jsonMethod = transformJavaMethod(javaService, javaMethod)

                        jsonMethod.isJavaService = true

                        jsonService.methods << jsonMethod
                    } catch (Exception e) {
                        jsonProject.errors << new JsonError(e)
                    }
                }

                jsonService.methods && (jsonServices << jsonService)
            } catch (Exception e) {
                jsonProject.errors << new JsonError(e)
            }
        }

        filterHtmlInSummary(jsonServices)

        return jsonServices
    }

    static JsonMethod transformJavaMethod(JavaInterface javaService, JavaMethod javaMethod) {
        def jsonMethod = new JsonMethod(javaMethod)

        jsonMethod.tags.addAll(javaService.javadocTags)

        javaService.javadocTags.each { jt ->
            if (jsonMethod.tags.any {
                it.tagName == jt.tagName
            }) {
                return
            }

            jsonMethod.tags << jt
        }

        jsonMethod.parameters = transformJavaFields(javaMethod.parameters)

        jsonMethod.parameterConstraints = JsonConstraintTransformer.transform(jsonMethod.parameters)

        jsonMethod.responses = [JsonStructTransformer.transformJavaAbstractTypeToJsonStruct(javaMethod.response)]

        jsonMethod.exceptions = transformJavaFields(javaMethod.exceptions)

        return jsonMethod
    }

    static List<JsonStruct> extractJsonStructs(List<JsonService> jsonServices) {
        List<JsonStruct> structs = []

        Closure iterator

        iterator = { JsonStruct jsonStruct ->
            if (!jsonStruct) {
                return
            }

            if (jsonStruct.isStructType && !jsonStruct.notExists) {
                structs << jsonStruct
            }

            if (jsonStruct.isEnumType && !jsonStruct.notExists) {
                structs << jsonStruct
            }

            jsonStruct.children.each { child ->
                if (child.isStructType) {
                    structs << child
                }

                if (child.isEnumType) {
                    structs << child
                }

                iterator(child)
            }
        }

        jsonServices.each { jsonService ->
            jsonService.methods.each { jsonMethod ->
                jsonMethod.parameters.each {
                    iterator(it)
                }

                jsonMethod.responses.each {
                    iterator(it)
                }
            }
        }

        return structs
    }

    static List<JsonStruct> transformJavaFields(List<JavaField> javaFields) {
        List<JsonStruct> jsonStructs = []

        javaFields.each { javaField ->
            jsonStructs << JsonStructTransformer.transformJavaFieldToJsonStruct(javaField)
        }

        return jsonStructs
    }

    static isRestfulApi(JavaComponent javaComponent, JavaMethod javaMethod) {
        return javaComponent.springAnnotations
                || javaComponent.jaxRSAnnotations
                || javaComponent.swaggerAnnotations
                || javaComponent.openAPIAnnotations
                || javaMethod.springAnnotations
                || javaMethod.jaxRSAnnotations
                || javaMethod.swaggerAnnotations
                || javaMethod.openAPIAnnotations
    }
}