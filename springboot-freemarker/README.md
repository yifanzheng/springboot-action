# SpringBoot整合freemarker模板引擎
- 修改pom.xml文件，添加Freemarker项目依赖
```java
<!-- 引入 freemarker 模板依赖 -->
<dependency>
	<groupId>org.springframework.boot</groupId>
	<artifactId>spring-boot-starter-freemarker</artifactId>
</dependency>
```
- 在`application.properties`文件中进行freemarker的相关配置
```java
#设定ftl文件路径,在resources目录下创建相关目录
spring.freemarker.template-loader-path=classpath:/templates/freemarker
#关闭缓存, 即时刷新, 上线生产环境需要改为true
spring.freemarker.cache=false
spring.freemarker.charset=UTF-8
spring.freemarker.check-template-location=true
spring.freemarker.content-type=text/html
spring.freemarker.expose-request-attributes=true
spring.freemarker.expose-session-attributes=true
spring.freemarker.request-context-attribute=request
#文件后缀名
spring.freemarker.suffix=.ftl
```
- freemarker实现资源访问
```java
@Controller
@RequestMapping("ftl")
public class DemoController {

    @Autowired
    private User user;

    @RequestMapping("/index")
    public String index(){
        //访问静态的页面
        return "index";
    }

    @RequestMapping("/center")
    public String center(ModelMap map){
        //ModelMap相当于springMvc中的ModelAndView
        map.addAttribute("user",user);
        //访问动态的页面
        return "center";
    }
}
```
