package com.evalon4j.visitors

class DependencyTree {
    List<String> qualifiedNames = []

    String qualifiedName

    DependencyTree parent = null

    boolean isRecursive(String qualifiedName) {
        def root = parent

        while (root) {
            if (root.qualifiedName == qualifiedName) {
                return true
            } else {
                root = root.parent
            }
        }

        return false
    }

    boolean isRecursive(String qualifiedName, List<String> qualifiedNames) {
        def root = parent

        while (root) {
            if (!root.qualifiedNames.intersect(qualifiedNames).isEmpty() || root.qualifiedNames.contains(qualifiedName) || root.qualifiedName == qualifiedName) {
                return true
            } else {
                root = root.parent
            }
        }

        return false
    }
}
