# Evalon4J - 通过命令行直接生成Java项目API文档

## 介绍

Evalon4J是付费产品[RabiAPI](https://gitee.com/RabiAPI/RabiAPISupport)使用的底层框架，通过静态分析源代码，直接生成相对应的接口文档。

和大多数开源框架不同，Evalon4J使用JavaParser直接分析源代码，不需要使用任何Gradle或者Maven插件，所以你不会遇到任何和插件有关的烦人问题，更不会有任何版本冲突。

## 使用场景

> TODO

## 使用说明

Evalon4J是一个命令行工具，使用起来非常简单。

1. 下载软件压缩包
2. 解压并进入`bin`文件夹
3. 执行 `./evalon4j -p {{你的Java项目根目录}} -o markdown`

## 参数说明

- `-p`，指定需要生成文档的Java项目根目录，文档默认会生成在项目文件夹内
- `-o`，指定导出的文档格式，目前已支持 `markdown`
- `-c`，指定 `evalon4j.json` 配置文件位置，通常用不着

## 配置文件

你可以在项目根目录下提供一份 `evalon4j.json` 配置文件，运行时会自动读取。

```json
{
  "name": "", // 项目名称
  
  "author": "whitecoms0s_", // 项目维护者
  
  "version": "1.0", // 文档版本，默认为1.0
  
  "includedServices": [], // 需要导出的服务名称或全名，白名单
  
  "excludedServices": [], // 需要过滤的服务名称或全名，黑名单
  
  "dependencies": [], // 项目依赖的源码包路径列表，(规划中)
  
  "onlyHttpApi": false, // 仅导出HTTP接口
  
  "onlyJavaApi": false, // 仅导出RPC接口
  
  "locale": "zh", // en 或 zh，会覆盖系统语言设置，没有该字段默认使用系统语言设置
}
```

## 生成结果预览

> TODO

## 功能特色

- 纯命令行工具，与项目本身零耦合，使用方便
- 纯静态分析，无需事先编译，构建项目，极速生成
- 支持Java泛型，递归，继承等写法
- 支持不同框架的混合使用
- 完全支持标准Javadoc注释
- 完全支持枚举类型

## 支持的框架

- 原生Java Interface和Javadoc注释
- Spring MVC
- JAX-RS
- Swagger 2.0 注解
- OpenAPI 3.0 注解
- JSR303参数校验注解

## 支持的导出格式

- markdown (已完成)
- asciidoc (开发中)
- swagger 2.0 json 文件 (开发中)
- openapi 3.0 json 文件 (开发中)

## 如何参与这个项目

> TODO

## 技术支持

> 我和你一样讨厌看文档

$\color{red}{如果你有任何使用上的疑问，请随时加入技术支持群与我沟通。}$

**产品交流QQ群: 244365684**

> ![img](https://i.imgur.com/HpQ6gql.png)