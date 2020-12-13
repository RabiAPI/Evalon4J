package com.evalon4j.utils

class FileUtils {
    private static String GRADLE_BUILD_FILE = "build.gradle"

    private static String MAVEN_BUILD_FILE = "pom.xml"

    static boolean isJavaFile(File file) {
        return file.isFile() && file.name.endsWith("java")
    }

    static boolean isGradleProject(File projectDir) {
        return projectDir.listFiles().any {
            it.name == GRADLE_BUILD_FILE
        }
    }

    static boolean isMavenProject(File projectDir) {
        return projectDir.listFiles().any {
            it.name == MAVEN_BUILD_FILE
        }
    }

    static List<File> getJavaModules(File projectDir) {
        List<File> modules = []

        String BUILD_FILE = null

        if (isGradleProject(projectDir)) {
            BUILD_FILE = GRADLE_BUILD_FILE
        }

        if (isMavenProject(projectDir)) {
            BUILD_FILE = MAVEN_BUILD_FILE
        }

        projectDir.eachFileRecurse { File file ->
            if (file.name == BUILD_FILE) {
                if (file.parentFile == projectDir) { // 跳过自身
                    return
                }

                modules << file.parentFile
            }
        }

        if (modules.isEmpty()) {
            modules << projectDir
        }

        return modules
    }
}
