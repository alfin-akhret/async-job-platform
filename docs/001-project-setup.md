# Project setup

## 1. Install JDK
We use java 21 via [Temurin](https://adoptium.net/en-GB/temurin) (JRE and JDK)
```
brew install --cask temurin@21
```

## 2. Install maven
[Maven](https://maven.apache.org) is build tools for java project created by Apache. 
```
brew install maven
```

## 3. Create pom.xml
This file will act as parent and aggregator.
see ../pom.xml
```
<modelVersion>4.0.0</modelVersion>
<groupId></groupId>
<artifactId></artifactId>
<version></version>
<packaging>pom</packaging>
```