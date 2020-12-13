package com.evalon4j


import com.evalon4j.exceptions.DirectoryNotFoundException
import com.evalon4j.exceptions.Evalon4JException
import com.evalon4j.java.JavaModule
import com.evalon4j.java.JavaProject
import com.evalon4j.json.JsonError
import com.evalon4j.utils.ExceptionUtils
import com.evalon4j.utils.FileUtils
import com.evalon4j.visitors.JavaReferenceBuilder
import com.evalon4j.visitors.JavaReferenceVisitor
import com.evalon4j.visitors.JavaVisitor
import com.github.javaparser.JavaParser

class Evalon4JParser {
    Evalon4JResult parse(String projectPath) {
        def result = new Evalon4JResult()

        try {
            def javaProject = new JavaProject()

            def projectDir = new File(projectPath)

            if (!projectDir.exists()) {
                throw new DirectoryNotFoundException()
            }

            javaProject.projectName = projectDir.name

            javaProject.projectPath = projectDir.path

            List<File> moduleDirs = FileUtils.getJavaModules(projectDir)

            def JavaParser = new JavaParser()

            List<JsonError> parseErrors = []

            moduleDirs.each { moduleDir ->
                buildJavaModule(javaProject, moduleDir)

                moduleDir.eachFileRecurse { File file ->
                    if (!FileUtils.isJavaFile(file)) {
                        return
                    }

                    def parseResult = JavaParser.parse(file)

                    if (parseResult.successful) {
                        def unit = parseResult.result.get()

                        try {
                            new JavaReferenceVisitor(moduleDir.name).visit(unit, javaProject)
                        } catch (Exception e) { //
                            parseErrors << new JsonError(
                                    errorMessage: "Resolve File ${file.name} Exception: ${e.message}",
                                    stackTraces: ExceptionUtils.filterExceptions(e))
                        }
                    } else { // 处理由于编译问题造成的解析失败
                        parseResult.problems.each { problem ->
                            parseErrors << new JsonError(errorMessage: problem.message)
                        }
                    }
                }
            }

            moduleDirs.each { moduleDir ->
                moduleDir.eachFileRecurse { File file ->
                    if (!FileUtils.isJavaFile(file)) {
                        return
                    }

                    def parseResult = JavaParser.parse(file)

                    if (parseResult.successful) {
                        def unit = parseResult.result.get()

                        try {
                            new JavaVisitor(moduleDir.name, javaProject.references).visit(unit, javaProject)
                        } catch (Exception e) {
                            parseErrors << new JsonError(errorMessage: "Resolve File ${file.name} Exception: ${e.message}", stackTraces: ExceptionUtils.filterExceptions(e))
                        }
                    } else { // 处理由于编译问题造成的解析失败
                        parseResult.problems.each { problem ->
                            parseErrors << new JsonError(errorMessage: problem.message)
                        }
                    }
                }
            }

            javaProject.modules.sort { it.moduleName }

            new JavaReferenceBuilder().buildReference(javaProject) // TODO Exception Handle

            def jsonProject = Evalon4JTransformer.transform(javaProject) // TODO Exception Handle

            jsonProject.errors.addAll(parseErrors)

            jsonProject.errors.addAll(javaProject.errors)

            result.data = jsonProject

        } catch (Evalon4JException e) {
            result.hasError = true

            result.errorCode = e.errorCode

            result.errorMessage = e.errorMessage
        } catch (Exception e) {
            result.hasError = true

            result.errorMessage = e.toString()

            result.errorTraces = ExceptionUtils.filterExceptions(e)
        } catch (Error e) {
            result.hasError = true

            result.errorCode = e.toString()

            result.errorMessage = "Parse Project Error"

            result.errorTraces = ExceptionUtils.filterExceptions(e)
        }

        return result
    }

    static void buildJavaModule(JavaProject javaProject, File moduleDir) {
        javaProject.modules << new JavaModule(moduleName: moduleDir.name)
    }
}
