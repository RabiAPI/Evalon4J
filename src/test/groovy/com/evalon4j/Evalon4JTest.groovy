package com.evalon4j


import org.junit.Test

class Evalon4JTest {
    @Test
    void testCompile() {
        String projectPath = "/Users/whitecosm0s/Repository/MultipleModuleProject"

        println Evalon4J.compile(projectPath)
    }

    @Test
    void testSpringRestfulController() {
        String projectPath = "/Users/whitecosm0s/Repository/SpringMVCExample"

        println Evalon4J.compile(projectPath)
    }

    @Test
    void testEvalon4JExampleProject() {
        String projectPath = "/Users/whitecosm0s/Repository/evalon4j-example-project_renamed"

        println Evalon4J.compile(projectPath)
    }

    @Test
    void testRabiApiDemoProject() {
        String projectPath = "/Users/whitecosm0s/Repository/rabiapi-demo-project"

        String doc = Evalon4J.compile(projectPath)

        File f = new File("/Users/whitecosm0s/Desktop/rabiapi-demo-project.json")

        f.write(doc)
    }

    @Test
    void testRabiApiDemoProjectForDebug() {
        String projectPath = "/Users/whitecosm0s/Repository/rabiapi-demo-project"

        Evalon4J.compile(projectPath)
    }

    @Test
    void testSwaggerPetStore() {
        String projectPath = "/Users/whitecosm0s/Repository/pet-store"

        String doc = Evalon4J.compile(projectPath)

        println doc
    }

    @Test
    void testApiDocTest() {
        String projectPath = "/Users/whitecosm0s/Repository/api-doc-test"

        println Evalon4J.compile(projectPath)
    }

    @Test
    void testSourceJar() {
        String projectPath = "/Users/whitecosm0s/Downloads/commons-lang3-3.10-sources.jar"
    }
}
