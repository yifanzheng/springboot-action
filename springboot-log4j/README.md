## Spring Boot 集成 Log4j 日志框架

Java 有很多日志系统，如 Java Util Logging, Log4J, Log4J2, Logback 等。Spring Boot 默认的日志框架是 Java Util Logging，并且 Spring Boot 也支持 Log4J、Logback 这些比较流行的日志框架。

Java 应用中，日志一般分为以下 5 个级别：

- ERROR 错误信息
- WARN 警告信息
- INFO 一般信息
- DEBUG 调试信息
- TRACE 跟踪信息

### Spring Boot 默认日志框架

默认情况下，Spring Boot 使用 logback 作为应用日志框架。因为 spring-boot-starter 其中包含了 spring-boot-starter-logging，该依赖内容就是 Spring Boot 默认的日志框架 Logback。

**配置日志**

在 `application.properties` 文件中添加配置，如：

```properties
# 日志输出级别是 info
logging.level.root = INFO
```
如果想控制特定包的输出级别，也可以配置 配置 logging.level.*  来决定哪些包的输出级别。

```properties
# 日志输出级别是 info
logging.level.root = INFO
logging.level.org.springframework.web = DEBUG
```

**输出日志文件**

默认情况下， Spring Boot 日志只会输出到控制台，并不会写入到日志文件。我们可以在application.properites 文件中配置 logging.file 文件名称和 logging.path 文件路径，将日志输出到日志文件中。

```properties
# 日志输出级别是 info
logging.level.root = INFO
logging.level.org.springframework.web = DEBUG
logging.path = ${user.home}/logs
logging.file = logging.log
```

### 使用 Log4j 记录日志

**添加依赖项**

在 pom.xml 文件中添加 Log4j 依赖，并移除默认的 Logback 依赖。

```xml
<dependency>
	<groupId>org.springframework.boot</groupId>
	<artifactId>spring-boot-starter</artifactId>
	<exclusions>
		<!-- 排除自带的logback依赖 -->
		<exclusion>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-logging</artifactId>
		</exclusion>
	</exclusions>
</dependency>
<!-- spring boot-log4j -->
<dependency>
	<groupId>org.springframework.boot</groupId>
	<artifactId>spring-boot-starter-log4j</artifactId>
</dependency>
```

**新建 log4j.propertie 文件**

在 `resource` 文件下，新建 `log4j.properties` 文件用于日志配置。

```properties
# 定义输出的日志级别
log4j.rootLogger=info,error,CONSOLE,DEBUG
# 表示将输出位置设定在控制台
log4j.appender.CONSOLE=org.apache.log4j.ConsoleAppender     
log4j.appender.CONSOLE.layout=org.apache.log4j.PatternLayout     
log4j.appender.CONSOLE.layout.ConversionPattern=%d{yyyy-MM-dd-HH-mm} [%t] [%c] [%p] - %m%n     
log4j.logger.info=info
log4j.appender.info=org.apache.log4j.DailyRollingFileAppender
log4j.appender.info.layout=org.apache.log4j.PatternLayout     
log4j.appender.info.layout.ConversionPattern=%d{yyyy-MM-dd-HH-mm} [%t] [%c] [%p] - %m%n  
log4j.appender.info.datePattern='.'yyyy-MM-dd
log4j.appender.info.Threshold = info   
log4j.appender.info.append=true
# 指定info类型的日志保存地址
log4j.appender.info.File=g:/logs/info/api_services_info
log4j.logger.error=error  
log4j.appender.error=org.apache.log4j.DailyRollingFileAppender
log4j.appender.error.layout=org.apache.log4j.PatternLayout     
log4j.appender.error.layout.ConversionPattern=%d{yyyy-MM-dd-HH-mm} [%t] [%c] [%p] - %m%n  
log4j.appender.error.datePattern='.'yyyy-MM-dd
log4j.appender.error.Threshold = error   
log4j.appender.error.append=true   
# 指定error类型的日志保存地址
#log4j.appender.error.File=/home/admin/pms-api-services/logs/error/api_services_error
log4j.appender.error.File=g:/logs/error/api_services_error
log4j.logger.DEBUG=DEBUG
log4j.appender.DEBUG=org.apache.log4j.DailyRollingFileAppender
log4j.appender.DEBUG.layout=org.apache.log4j.PatternLayout     
log4j.appender.DEBUG.layout.ConversionPattern=%d{yyyy-MM-dd-HH-mm} [%t] [%c] [%p] - %m%n  
log4j.appender.DEBUG.datePattern='.'yyyy-MM-dd
log4j.appender.DEBUG.Threshold = DEBUG   
log4j.appender.DEBUG.append=true   
# 指定DEBUG类型的日志保存地址
log4j.appender.DEBUG.File=g:/logs/debug/api_services_debug
```



