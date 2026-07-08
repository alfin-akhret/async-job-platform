# 001 - Project Setup

## Overview

This project is built as a multi-module Maven project to simulate the architecture commonly found in enterprise Java applications.

The goal is to learn not only Java and Spring Boot, but also how production-grade backend systems are structured.

---

## Why Multi-Module Maven?

We chose a multi-module Maven project instead of a single-module project because:

- Better separation of concerns.
- Clear module boundaries.
- Easier dependency management.
- Independent services can share common libraries.
- Similar to how many enterprise Java projects are organized.

Current modules:

- api
- worker
- common

Future modules may include:

- scheduler
- integration
- monitoring

---

## Why `<packaging>pom</packaging>`?

The root project is not intended to produce an executable application.

Instead, it acts as:

- Parent POM
- Aggregator POM

Using `pom` packaging allows Maven to:

- Build all child modules together.
- Share dependency versions.
- Share plugin configuration.
- Centralize project configuration.

---

## Parent POM vs Aggregator POM

Although they often exist in the same file, they serve different responsibilities.

### Parent POM

Responsible for sharing configuration with child modules.

Examples:

- Java version
- Dependency versions
- Plugin versions
- Common build configuration

### Aggregator POM

Responsible for grouping multiple modules into a single build.

For example:

```xml
<modules>
    <module>common</module>
    <module>api</module>
    <module>worker</module>
</modules>

Running
```
mvn clean install
```

from the root project builds every module automatically.