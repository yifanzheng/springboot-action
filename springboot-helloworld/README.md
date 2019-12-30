## Spring Boot 快速上手

### 简介

- 微框架，与 Spring4 一起诞生，比如 @RestController。
- 可以快速上手，整合了一些子项目（开源框架或者第三方开源库）。
- 可以依赖很少的配置就可以十分快速的搭建并运行项目。
- 完全注解化，简化 XML 配置，不需要 web.xml 文件，内置嵌入 HTTP 服务器。

### Spring Boot 特点

- 基于 Spring，使开发者快速上手，门槛低（Spring 全家桶）。
- Spring Boot 可以创建独立运行的应用而不依赖容器。
- 不需要打包成 war 包，可以放入 Tomcat 中直接运行。
- 提供 Maven 极简配置，但会引入许多不需要的包。
- 根据项目来依赖，从而配置 Spring，需要什么配什么。
- 提供可视化的相关功能，方便监控，比如性能、应用的健康程度等。
- 简化了配置，不用再看过多的 XML。
- 为微服务 Spring Cloud 铺路，Spring Boot 可以整合各式各样的框架来构建为服务，比如 Dubbo、Thrift 等。

### Spring Boot 使用场景

- 有 Spring 的地方都行
- J2EE/Web 项目
- 微服务

### Spring Boot Starter

Spring Boot Starter 它包含了一系列可以集成到应用里面的依赖包，可以一站式集成 Spring 及其他技术，而不需要到处找示例代码和依赖包。Spring Boot 官方的启动器都是以 spring-boot-starter-xxx 命名的，代表了一个特定的应用类型。下面是一些 Spring Boot 常用的依赖模块。

- spring-boot-starter-logging：使用 Spring Boot 默认的日志框架 Logback。
- spring-boot-starter-log4j：添加 Log4j 的支持。
- spring-boot-starter-web：支持 Web 应用开发，包含 Tomcat 和 spring-mvc。
- spring-boot-starter-tomcat：使用 Spring Boot 默认的 Tomcat 作为应用服务器。
- spring-boot-starter-jetty：使用 Jetty 而不是默认的 Tomcat 作为应用服务器。
- spring-boot-starter-test：包含常用的测试所需的依赖，如 JUnit、Hamcrest、Mockito 和 spring-test 等。
- spring-boot-starter-aop：包含 spring-aop 和 AspectJ 来支持面向切面编程 (AOP)。
- spring-boot-starter-security：包含 spring-security。
- spring-boot-starter-jdbc：支持使用 JDBC 访问数据库。
- spring-boot-starter-redis：支持使用 Redis。
- spring-boot-starter-data-mongodb：包含 spring-data-mongodb 来支持 MongoDB。
- spring-boot-starter-data-jpa：包含 spring-data-jpa、spring-orm 和 Hibernate 来支持 JPA。
- spring-boot-starter-amqp：通过 spring-rabbit 支持 AMQP。
- spring-boot-starter-actuator：添加适用于生产环境的功能，如性能指标和监测等功能。

### Spring Boot & Spring Cloud

- Spring Boot 其实是一个快速开发框架，能够帮助我们**快速整合第三方常用框架，完全采用注解化，简化 XML 配置，最终以 Java 应用程序进行执行。**

- Spring Cloud 目前是一套完整微服务解决框架，功能强大。如：注册中心，客户端调用工具，服务治理（负载均衡、断路器、分布式配置中心）。

- Spring Boot 的 Web组件默认集成 Spring MVC；Spring Cloud 依赖于Spring Boot 实现微服务，使用 Spring MVC 编写微服务接口。

### 注意

- **Spring Boot 只是一个快速开发框架，不是微服务框架！**  

- **Spring Boot + Spring Cloud 实现微服务开发！（RPC 远程通讯技术）**

### Spring Boot 初体验

**快速搭建**

借助 Spring 官网：[https://start.spring.io/](https://start.spring.io/) 进行快速搭建。

![SpringInit](../asset/imgs/spring-init.png)  

点击 Generate，然后就会有一个 zip 包下载到本地，进行解压，导入到 IDE 即可开发。

**手动搭建**

- 创建一个 POM 文件。

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 https://maven.apache.org/xsd/maven-4.0.0.xsd">
	<modelVersion>4.0.0</modelVersion>
	<parent>
		<groupId>org.springframework.boot</groupId>
		<artifactId>spring-boot-starter-parent</artifactId>
		<version>2.0.4.RELEASE</version>
		<relativePath/> 
	</parent>
	<groupId>com.example</groupId>
	<artifactId>demo</artifactId>
	<version>0.0.1-SNAPSHOT</version>
	<name>hello</name>
	<description>Demo project for Spring Boot</description>

	<properties>
		<java.version>1.8</java.version>
	</properties>

	<dependencies>
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter</artifactId>
		</dependency>
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-test</artifactId>
			<scope>test</scope>
		</dependency>
	</dependencies>

	<build>
		<plugins>
			<plugin>
				<groupId>org.springframework.boot</groupId>
				<artifactId>spring-boot-maven-plugin</artifactId>
			</plugin>
		</plugins>
	</build>

</project>
```
这里引入 `spring-boot-maven-plugin` 插件是为了在使用 `mvn package` 打包时，会生成一个直接可以运行的 JAR 包文件，使用 `java -jar` 命令可以直接运行。

- 创建 Spring Boot 应用主类

使用注解 `@SpringBootApplication` 标记该类，让 Spring Boot 自动给程序进行配置。

```java
@SpringBootApplication
public class HelloApplication {
 
    public static void main(String[] args) throws Exception {
        
        SpringApplication.run(ApplicationDemo.class, args);
    }
}
```

- 搭建 Web 工程  

在 POM 文件中引入 `spring-boot-starter-web` 依赖，使程序支持 Web 应用开发。

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-web</artifactId>
</dependency>
```

- 创建 API

```java
@RestController
@RequestMapping("/api")
public class HelloWorldResource {

    @GetMapping("/hello")
    public ResponseEntity<String> helloWorld() {
        String message = "Hello World!";
        return ResponseEntity.ok(message);
    }
}
```



