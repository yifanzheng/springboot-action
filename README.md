# SpringBoot简介

- 微框架，与spring4一起诞生，比如@RestController。
- 可以快速上手，整合了一些子项目（开源框架或者第三方开源库）。
- 可以依赖很少的配置就可以十分快速的搭建并运行项目。
- 完全注解化，简化XML配置，不需要web.xml文件，内置嵌入Http服务器。

### SpringBoot特点

- 基于spring，使开发者快速上手，门槛低（spring全家桶）
- Springboot可以创建独立运行的应用而不依赖容器
- 不需要打包成war包，可以放入Tomcat中直接运行
- 提供maven极简配置，但会引入许多不需要的包
- 根据项目来依赖，从而配置spring，需要什么配什么
- 提供可视化的相关功能，方便监控，比如性能、应用的健康程度等
- 简化了配置，不用再看过多的Xml
- 为微服务SpringCloud铺路，SpringBoot可以整合各式各样的框架来构建为服务，比如dubbo、thrift等

### SpringBoot使用场景

- 有spring的地方都行
- J2EE/web项目
- 微服务

### SpringBoot的项目结构

- Application.java 放在根目录，主要用于一些框架配置
- domain目录 放实体Entity、数据访问层repository
- service目录 主要放业务代码
- controller目录 负责页面访问控制
- pom.xml文件 主要添加依赖
  两大默认模块：
  ```java
  spring-boot-starter:核心模块，包括自动配置支持、日志和yml
  spring-boot-starter-test:测试模块，包括Junit、mockito、Hamcrest
  ```
### SpringBoot与SpringCloud的关系
- SpringBoot其实是一个快速开发框架，能够帮助我们**快速整合第三方常用框架，完全采用注解化，简化xml配置，最终以java应用程序进行执行。**

- SpringCloud目前是一套完整微服务解决框架，功能强大。如：注册中心，客户端调用工具，服务治理（负载均衡、断路器、分布式配置中心）。

- SpringBoot Web组件默认集成SpringMVC；SpringCloud依赖于SprinGBoot实现微服务，使用SpringMVC编写微服务接口。

##### SpringBoot只是一个快速开发框架，不是微服务框架！
##### SpringBoot+SpringCloud实现微服务开发！（RPC远程通讯技术）