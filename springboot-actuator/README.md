# springboot 搭建actuator监控中心，整合AdminUI平台

### springboot监控中心
- 针对微服务监控，服务器内存变化（堆内存、线程、日志管理等），检测服务配置是否可用（模拟访问），统计spring容器中的bean，统计http接口数量。
- Actuator监控应用（没有界面，返回json格式）。
- AdminUI底层使用Actuator监控应用，实现可视化界面。

### Actuator常用访问路径
| 路径 | 作用 |
|---|---|
| /actuator/beans | 显示应用程序中所有Spring bean的完整列表 |
| /actuator/configprops|显示所有配置信息。|
| /actuator/env	| 陈列所有的环境变量。|
| /actuator/mappings | 显示所有@RequestMapping的url整理列表。|
| /actuator/health | 显示应用程序运行状况信息 up表示成功 down失败。|
| /actuator/info | 查看自定义应用信息。|


### 配置Actuator服务
- 引入相关依赖
```java
<parent>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-parent</artifactId>
    <version>2.0.0.RELEASE</version>
    <relativePath/> 
</parent>
<dependencies>
    <dependency>
        <groupId>de.codecentric</groupId>
        <artifactId>spring-boot-admin-starter-server</artifactId>
        <version>2.0.0</version>
    </dependency>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-webflux</artifactId>
    </dependency>
    <!-- Spring Boot Actuator对外暴露应用的监控信息，Jolokia提供使用HTTP接口获取JSON格式的数据 -->
    <dependency>
        <groupId>org.jolokia</groupId>
        <artifactId>jolokia-core</artifactId>
    </dependency>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-actuator</artifactId>
    </dependency>
    <dependency>
        <groupId>com.googlecode.json-simple</groupId>
        <artifactId>json-simple</artifactId>
        <version>1.1</version>
    </dependency>
</dependencies>
```

### 配置Client端，将应用部署到AdminUI平台
- 添加依赖
```java
<parent>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-parent</artifactId>
    <version>2.0.0.RELEASE</version>
    <relativePath/>
</parent>

<properties>
    <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
    <project.reporting.outputEncoding>UTF-8</project.reporting.outputEncoding>
    <java.version>1.8</java.version>
</properties>

<dependencies>
    <dependency>
        <groupId>de.codecentric</groupId>
        <artifactId>spring-boot-admin-starter-client</artifactId>
        <version>2.0.0</version>
    </dependency>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-actuator</artifactId>
    </dependency>
    <dependency>
        <groupId>org.jolokia</groupId>
        <artifactId>jolokia-core</artifactId>
    </dependency>
    <dependency>
        <groupId>com.googlecode.json-simple</groupId>
        <artifactId>json-simple</artifactId>
        <version>1.1</version>
    </dependency>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-web</artifactId>
    </dependency>
</dependencies>
```
- 配置application.yml文件
```java
## 配置client端，注册到admin ui平台
spring:
  boot:
    admin:
      client:
        url: http://localhost:8080
server:
  port: 8081

## 配置启用所有的监控端点，默认情况下，这些端点是禁用的
management:
  endpoints:
    web:
      exposure:
        include: "*"
  endpoint:
    health:
      show-details: ALWAYS
```


