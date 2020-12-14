# Evalon4J - The Ultimate Java API Documentation Generator

## Introduction

Evalon4J is a framework developed for Mac App [RabiAPI](https://github.com/RabiAPI/RabiAPI-Support), you can directly
use it as a command tool for generating java api documentation.

I have used SpringFox before, it's needs a lot of configurations and I think markdown is enough for me, so I developed this tool.

If you like it **!!!PLEASE STAR THIS PROJECT!!!**

## How To Use

Evalon4J is a command line tool and very easy to use.

1. Download the zip file and extract it.
2. Get into the `bin` directory from terminal.
3. Execute `./evalon4j -p {{Your Java Project Path}} -o markdown`
4. Done

## Parameters

- `-p` specify the java project path, the output will be there and named as `evalon4j`
- `-o` specify the output format，now support: `markdown`, `swagger`, `openapi`
- `-c` specify a `evalon4j.json` configuration file, or just put it under your project root

## Configuration

You can also put a `evalon4j.json` file under your project root, or using `-c` to specify one.

```json
{
  "name": "Your awesome project",
  "author": "whitecoms0s_",
  "version": "1.0.0",
  "includedServices": [],
  "excludedServices": [],
}
```

### Road Map

- [ ] Export documentation as a static website
- [ ] Support `ApidocJS` Framework
- [ ] 

### Features

- Simple command line tool, no massive dependencies, I swear.
- Pure Static Analysis, no need to build or compile your project, no waste of time.
- Support Java generic, recursive, extension
- Complete support javadoc
- Complete support java enumeration

### Supported Frameworks

- Pure Java `interface` and javadoc, used for RPC Service
- Spring MVC
- JAX-RS
- Swagger 2.0 Annotations, like @ApiOperation
- OpenAPI 3.0 Annotations
- JSR303 Bean Validation Annotations

### Supported Export Format

- markdown
- swagger 2.0 json file
- openapi 3.0 json file

## Contact Me

You can join the gitter chat group.

[![Gitter](https://badges.gitter.im/RabiAPITool/community.svg)](https://gitter.im/RabiAPITool/community?utm_source=badge&utm_medium=badge&utm_campaign=pr-badge)

Or just open an issue.
