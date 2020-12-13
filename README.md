# Evalon4J - 通过命令行直接生成Java项目API文档

## 介绍

Evalon4J是付费产品[RabiAPI](https://gitee.com/RabiAPI/RabiAPISupport)使用的底层框架，通过静态分析源代码，直接生成相对应的接口文档。

## 使用说明

Evalon4J是一个命令行工具，使用起来非常简单。

1. 下载软件压缩包
2. 解压并进入`bin`文件夹
3. 执行 `./evalon4j -p {{你的Java项目根目录}} -o markdown`

## 参数说明

- `-p`，指定需要生成文档的Java项目根目录，文档默认会生成在项目文件夹内
- `-o`，指定导出的文档格式，目前一共四种: `markdown`, `asciidoc`, `swagger`, `openapi`

### 功能特色

- 纯命令行工具，与项目本身零耦合，使用方便
- 纯静态分析，无需事先编译，构建项目，极速生成
- 支持Java泛型，递归，继承等写法
- 支持不同框架的混合使用
- 完全支持标准Javadoc注释
- 完全支持枚举类型

### 支持的框架

- 原生Java Interface和Javadoc注释
- Spring MVC
- JAX-RS
- Swagger 2.0 注解
- OpenAPI 3.0 注解
- JSR303参数校验注解

### 支持的导出格式

- markdown
- asciidoc
- swagger 2.0 json 文件
- openapi 3.0 json 文件

## 技术支持

> 我和你一样讨厌看文档

$\color{red}{如果你有任何使用上的疑问，请随时加入技术支持群与我沟通。}$ 

**产品交流QQ群: 244365684**

> ![img](https://i.imgur.com/HpQ6gql.png)





