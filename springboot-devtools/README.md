# SpringBoot使用devtools实现热部署
### devtools(热部署)
- spring-boot-devtools 是一个为开发者服务的一个模块，其中最重要的功能就是自动应用代码更改到最新的App上面去。原理是在发现代码有更改之后，重新启动应用，但是速度比手动停止后再启动还要更快，更快指的不是节省出来的手工操作的时间。

- devtools可以实现页面热部署（即页面修改后会立即生效，这个可以直接在application.properties文件中配置spring.thymeleaf.cache=false来实现）
实现类文件热部署（类文件修改后不会立即生效），实现对属性文件的热部署。 
即devtools会监听classpath下的文件变动，并且会立即重启应用（发生在保存时机），
注意：因为其采用的虚拟机机制，该项重启是很快的 
（1）base classloader （Base类加载器）：加载不改变的Class，例如：第三方提供的jar包。
（2）restart classloader（Restart类加载器）：加载正在开发的Class

- 为什么重启很快，因为重启的时候只是加载了在开发的Class，没有重新加载第三方的jar包


### 使用方法
- 在pom.xml文件中，添加项目devtools依赖
```java
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
    <!--没有该项配置，devtools不会起作用-->
    <configuration>
        <fork>true</fork>
    </configuration>
 </plugin>
```
- 在资源文件application.properties中进行devtools的相关配置
```java
#页面修改后立即生效，关闭缓存，立即刷新
spring.thymeleaf.cache=false
#热部署生效
spring.devtools.restart.enabled=true
#设置需要重启的目录
spring.devtools.restart.additional-paths=src/java/main
#设置不需要重启的目录
spring.devtools.restart.exclude=static/**,public/**,WEB-INF/**
#为mybatis设置，生产环境可删除
#restart.include.mapper=/mapper-[\\w-\\.]+jar
#restart.include.pagehelper=/pagehelper-[\\w-\\.]+jar
```