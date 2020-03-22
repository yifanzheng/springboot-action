## Spring Boot 集成 FreeMarker 模板引擎

Spring Boot 提供了很多模板引擎的支持，例如 FreeMarker、Thymeleaf。下面，我们来看看 Spring Boot 如何集成 FreeMarker 模板引擎。


### 添加 FreeMarker 依赖

```java
<!-- 引入 freemarker 模板依赖 -->
<dependency>
	<groupId>org.springframework.boot</groupId>
	<artifactId>spring-boot-starter-freemarker</artifactId>
</dependency>
```

### 配置 FreeMarker

在`application.properties`文件中进行FreeMarker 的相关配置。

```java
# 设定ftl文件路径,在resources目录下创建相关目录
spring.freemarker.template-loader-path=classpath:/templates/freemarker
# 关闭缓存, 即时刷新, 上线生产环境需要改为true
spring.freemarker.cache=false
spring.freemarker.charset=UTF-8
spring.freemarker.check-template-location=true
spring.freemarker.content-type=text/html
spring.freemarker.expose-request-attributes=true
spring.freemarker.expose-session-attributes=true
spring.freemarker.request-context-attribute=request
# 文件后缀名
spring.freemarker.suffix=.ftl
```
### FreeMarker 实现资源访问

```java
/**
 * DemoController
 *
 * @author star
 **/
@Controller
@RequestMapping("ftl")
public class DemoController {

    @Autowired
    private User user;

    @RequestMapping("/index")
    public String index() {
        // 访问静态的页面
        return "index";
    }

    @RequestMapping("/center")
    public String center(ModelMap map) {
        // ModelMap相当于springMVC中的ModelAndView
        map.addAttribute("user", user);
        // 访问动态的页面
        return "center";
    }
}
```

关于 FreeMarker 模板语法，可以查看 FreeMarker 开发手册。

### 参考

[FreeMarker 开发手册](http://freemarker.foofun.cn/dgui_template_exp.html)
