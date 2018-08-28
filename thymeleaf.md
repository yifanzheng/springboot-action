# SpringBoot整合thymeleaf模板引擎
### thymeleaf 静态资源配置
```java
#设定文件路径
spring.thymeleaf.prefix=calsspath:/templates
#加载的文件后缀名
spring.thymeleaf.suffix=.html
spring.thymeleaf.mode=HTML5
spring.thymeleaf.encoding=UTF-8
spring.thymeleaf.content-type=text/html
#关闭缓存，及时刷新，上线生产环境改为true
spring.thymeleaf.cache=false
```
### 在pom.xml文件添加thymeleaf 项目依赖
```java
<dependency>
      <groupId>org.springframework.boot</groupId>
      <artifactId>spring-boot-starter-thymeleaf</artifactI>
</dependency>
```
### thymeleaf 常用标签使用方式
- 表达式
```java
${} ：可用值表达式。
*{} ：所有值表达式，比如*{name},先从可用值查找，如果有上下文，上层是Object,则查找Object中的name属性值。
#{} ：消息表达式，国际化时使用，也可使用内置对象，如dates格式化数据。
@{} ：链接表达式，配合link,src,href使用。
~{} ：片段表达式，用来引入公共部分的代码片段，并进行传值操作。
```

