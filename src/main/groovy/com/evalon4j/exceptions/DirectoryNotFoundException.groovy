package com.evalon4j.exceptions

import com.evalon4j.Evalon4J

class DirectoryNotFoundException extends Evalon4JException {
    String errorCode = "DIRECTORY_NOT_FOUND"

    String errorMessage = "该文件夹或文件不存在"
}
