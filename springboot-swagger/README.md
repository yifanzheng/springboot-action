## Spring Boot 集成 Swagger2 构建 API 文档

随着前后端分离的开发模式越来越流行，我们往往需要给前端或者第三方提供接口，这个时候我们就需要给他们提供一份详细的 API 说明文档。但是维护一份详细的文档本身就是很费时费力的事，并且随着时间推移，不断修改接口实现的时候都必须同步更新接口文档，而文档与代码是分离，不然很容易导致不一致现象。

不过，我们通过 Swagger 可以自动生成 Restful API 文档，它不仅可以减少我们创建文档的工作量，同时文档内容又整合到实现代码中，可以让我们在修改代码逻辑的同时方便的修改文档说明。下面，我们将介绍使用 Spring Boot 集成 Swagger。

### 添加 Swagger 依赖

```xml
<!-- Swagger2 -->
  <dependency>
    <groupId>io.springfox</groupId>
    <artifactId>springfox-swagger2</artifactId>
    <version>2.9.2</version>
  </dependency>
  <dependency>
    <groupId>io.springfox</groupId>
    <artifactId>springfox-swagger-ui</artifactId>
    <version>2.9.2</version>
  </dependency>
  <dependency>
    <groupId>io.springfox</groupId>
    <artifactId>springfox-bean-validators</artifactId>
    <version>2.9.2</version>
  </dependency>
<!-- ./ Swagger2 -->
```

### 创建配置类配置 Swagger

- Swagger2Config

```java
@Configuration
@EnableSwagger2
public class Swagger2Config {

    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                // 控制层路径，即 API 接口文件存放位置
                .apis(RequestHandlerSelectors.basePackage("com.example.swagger.web.rest"))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Spring Boot Swagger2")
                // 联系人信息
                .contact(new Contact("Star.Y.Zheng", "http://yifanstar.top/", "zhengyifan1996@outlook.com"))
                .version("1.0")
                .build();
    }

}
```
- WebCorsConfig

指定根路径为 Swagger 默认访问路径。

```java
@Configuration
public class WebCorsConfig implements WebMvcConfigurer {
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("redirect:/swagger-ui.html");
        registry.setOrder(Ordered.HIGHEST_PRECEDENCE);
        WebMvcConfigurer.super.addViewControllers(registry);
    }
}
```

### 查看效果

启动 Spring Boot 程序，通过浏览器访问 `http://127.0.0.1:8080/` 地址，即可出现如下画面。

![swagger](/asset/imgs/swagger.png)

### API 详情配置

Swagger 还提供了一些注解 API，帮助我们对 API 进行详细的文字描述，如对 API 功能、返回值、参数含义等进行描述。

我们可以通过 `@Api`、`@ApiOperation`、`@ApiImplicitParam`、`@ApiModel`、`@ApiModelProperty` 注解来给 API 进行说明。

- 示例

```java
@RestController
@RequestMapping("/api")
@Api(tags = "用户资源")
public class UserResource {

    @Autowired
    private UserService userService;

    @GetMapping("/users")
    @ApiOperation(value = "获取用户列表", notes = "获取用户的列表信息")
    public ResponseEntity<List<UserDTO>> listUsers() {
        List<UserDTO> userDTOS = userService.listUsers();
        return ResponseEntity.ok(userDTOS);
    }

    @GetMapping("/users/{id}")
    @ApiOperation(value = "获取用户信息", notes = "根据指定id获取用户信息")
    @ApiImplicitParam(name = "id", value = "用户编号", dataType = "Integer", required = true)
    public ResponseEntity<UserDTO> getUser(@PathVariable(value = "id") Integer id) {
        UserDTO userDTO = userService.getUser(id);
        return ResponseEntity.ok(userDTO);
    }

    @PostMapping("/users")
    @ApiOperation(value = "保存用户", notes = "保存用户信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "用户编号", dataType = "Integer", required = true),
            @ApiImplicitParam(name = "userName", value = "用户姓名", dataType = "String", required = true),
            @ApiImplicitParam(name = "age", value = "用户年龄", dataType = "Integer", required = true),
            @ApiImplicitParam(name = "email", value = "用户编号", dataType = "String", required = true)
    })
    public ResponseEntity<Void> saveUser(@RequestBody UserDTO userDTO) {
        userService.saveUser(userDTO);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/users/{id}")
    @ApiOperation(value = "删除用户", notes = "根据指定id删除用户")
    public ResponseEntity<Void> deleteUser(@PathVariable(value = "id") Integer id) {
        userService.deleteUser(id);
        return ResponseEntity.ok().build();
    }

}
```
完成上面配置后，启动 Spring Boot 程序，访问 `http://localhost:8080/` 地址，就能看到带中文说明的文档了。

![swagger-name](/asset/imgs/swagger-name.png)

![swagger-model](/asset/imgs/swagger-model.png)

### API 调用

在上图页面中，我们看到每个 API 地址下都有 `Try it out` 按钮，点击它以后就会生成请求模版，里面有请求参数的数据格式，我们稍加修改，填上对应的值，点击 `Execute` 按钮，即可完成请求调用。

![swagger-get](/asset/imgs/swagger-get.png)

相比使用 postman，通过 Swagger 来测试 API 会更加方便。

虽然通过该文档是可以直接访问 API 接口，但我们不能把接口直接暴露在生产环境中，尤其是要对外提供服务的系统。所以我们在生产环境需要关闭此功能，我已经在源码中进行了配置。


