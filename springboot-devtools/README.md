## Spring Boot 使用 devtools 实现热部署

### devtools（热部署）

- spring-boot-devtools 是一个为开发者服务的一个模块，其中最重要的功能就是自动应用代码更改到最新的 App 上面去。原理是在发现代码有更改之后，重新启动应用，但是速度比手动停止后再启动还要更快，更快指的不是节省出来的手工操作的时间。

- devtools 可以实现页面热部署（即页面修改后会立即生效，这个可以直接在 application.properties 文件中配置 spring.thymeleaf.cache=false 来实现）实现类文件热部署（类文件修改后不会立即生效），实现对属性文件的热部署。即 devtools 会监听 Classpath 下的文件变动，并且会立即重启应用（发生在保存时机）。

- 由于采用的虚拟机机制，重启的时候只是加载了在开发的 Class，没有重新加载第三方的 JAR 包，所以重启是很快的。

**注意**  
 
- base classloader （Base 类加载器）：加载不改变的 Class，例如：第三方提供的 JAR 包。
- restart classloader（Restart 类加载器）：加载正在开发的 Class。


### 配置工程

- 在 pom.xml 文件中，添加项目 devtools 依赖

```xml
<!--热部署依赖-->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-devtools</artifactId>
    <optional>true</optional>
 </dependency>

<!--添加插件-->
<plugin>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-maven-plugin</artifactId>
    <!--没有该项配置，devtools 不会起作用-->
    <configuration>
        <fork>true</fork>
    </configuration>
 </plugin>
```
- 在资源文件 application.properties 中进行 devtools 的相关配置

```properties
# 页面修改后立即生效，关闭缓存，立即刷新
spring.thymeleaf.cache=false
# 热部署生效
spring.devtools.restart.enabled=true
# 设置需要重启的目录
spring.devtools.restart.additional-paths=src/java/main
# 设置不需要重启的目录
spring.devtools.restart.exclude=static/**,public/**,WEB-INF/**
# 为 mybatis 设置，生产环境可删除
# restart.include.mapper=/mapper-[\\w-\\.]+jar
# restart.include.pagehelper=/pagehelper-[\\w-\\.]+jar
```
当热部署搭建完成后，程序只要启动一次即可，后面修改代码时，只需保存后便可以触发热部署机制，自动重启。