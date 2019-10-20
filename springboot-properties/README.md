## Spring Boot 资源配置文件读取

在实际开发中，我们会遇到关于配置文件的读取，获取配置文件的自定义配置，以及如何多环境下的配置文件信息的获取。

### 配置读取优先级

1. 命令行参数。
2. 从 `java:comp/env` 得到的 JNDI 属性。
3. 通过 `System.getProperties()` 获取的 Java 系统参数。
4. 操作系统环境变量。
5. `RandomValuePropertySource` 配置的 `random.*` 属性值。如 ${random.int}、${random.long}、${random.value}、${random.uuid} 等。
6. JAR 包外部的 `application-{profile}.properties` 或 `application.yml` （带 spring.profile）配置文件。
7. JAR 包内部的 `application-{profile}.properties` 或 `application.yml` （带 spring.profile）配置文件。 
8. JAR 包外部的 `application.properties` 或 `application.yml`（不带 spring.profile）配置文件。
9. JAR 包内部的 `application.properties`或 `application.yml`（不带 spring.profile）配置文件。
（从 JAR 包外向 JAR 包内进行寻找，优先加载带 `{profile}` 的文件，再加载不带 `{profile}`的文件） 

10. `@Configuration` 注解类上的 `@PropertySource`。
11. 通过 `SpringApplication.setDefaultProperties` 指定默认属性。

注意：`application.properties` 文件的优先级高于 `application.yml` 文件的优先级。

### 配置依赖项

在 pom.xml 文件中，加入 `spring-boot-configuration-processor` 依赖项，用于读取配置值。

```xml
<dependency>
  <groupId>org.springframework.boot</groupId>
  <artifactId>spring-boot-configuration-processor</artifactId>
  <optional>true</optional>
</dependency>
```

### 属性配置与实体类的映射

- application.properties

```properties
# 32 位随机字符串
rand.stringValue = ${random.value}
# 随机的 int 类型数字
rand.intNumber = ${random.int}
# 随机的 long 类型数字
rand.longNumber = ${random.long}
# 100 以内的随机int类型
rand.number = ${random.int(100)}
# 0-100 范围内的随机int类型
rand.rangeNumber = ${random.int[0,100]}
```
- author.properties

```properties
author.name = yifanzheng
author.nickname = star
author.intro = good boy!
```

**方式一**  

在 `@Configuration` 注解的类上加上 `@ConfigurationProperties(prefix="前缀名")` 注解，可以使用 `@PropertySource` 注解指定加载的配置文件，不加时默认加载 `application.properties` 文件

```java
/**
 * AuthorConfig
 *
 * @author star
 **/
@Configuration
@ConfigurationProperties(prefix = "author")
@PropertySource(value = "classpath:author.properties") // 指定配置文件的位置
public class AuthorConfig {

    private String name;

    private String nickname;

    private String intro;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
}
```
注意：使用这种方式配置的类，在使用 @Autowired 注入时，不能直接 return 注入的对象，它只是指向 Spring 容器中对象资源的一个标识，可以通过这个标识返回该对象中的值。

**方式二** 

使用 `@Value` 注解，直接映射实体类的各个属性。

 ```java
/*
 * RandomConfig
 *
 * @author star
 **/
@Component
public class RandomConfig {

    @Value("${rand.stringValue}")
    private String stringValue;

    @Value("${rand.intNumber}")
    private Integer intNumber;

    @Value("${rand.longNumber}")
    private Long longNumber;

    @Value("${rand.number}")
    private Integer number;

    @Value("${rand.rangeNumber}")
    private Integer rangeNumber;

    public String getStringValue() {
        return stringValue;
    }

    public void setStringValue(String stringValue) {
        this.stringValue = stringValue;
    }

    public Integer getIntNumber() {
        return intNumber;
    }

    public void setIntNumber(Integer intNumber) {
        this.intNumber = intNumber;
    }

    public Long getLongNumber() {
        return longNumber;
    }

    public void setLongNumber(Long longNumber) {
        this.longNumber = longNumber;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public Integer getRangeNumber() {
        return rangeNumber;
    }

    public void setRangeNumber(Integer rangeNumber) {
        this.rangeNumber = rangeNumber;
    }
}
```

**方式三**  

在方法上使用 `@Bean` 注解，配合以上注解使用。

```java
/**
 * AuthorBeanConfig 使用 @Bean 注解的方式获取配置信息
 *
 * @author star
 **/
@Configuration
@PropertySource(value = "classpath:author.properties")
public class AuthorBeanConfig {

    @Bean
    @ConfigurationProperties(prefix = "author")
    public AuthorBean getAuthorBean(){
       return new AuthorBean();
    }
}
```
在没有使用 `@PropertySource` 注解指定加载的文件时，默认使用 `application.properties` 文件中与实体对象的属性相同的配置项。
`@PropertySources` 注解优先级比较低，即使指定了加载的文件，但出现与 `application.properties` 文件相同的配置项时会被其覆盖。

### 自定义 YAML 资源文件属性配置

- 创建 `user.yml` 文件，进行属性配置

```java
# 配置 user 对象的值
demo:
 user:
  name: star
  age: 22
  desc: good boy!
```
- 使用注解标记 `User.java` 对象文件

```java
/**
 * UserConfig
 *
 * @author star
 **/
@Configuration
@ConfigurationProperties(prefix = "demo.user") // 前缀名注释必须有，不然会报错
@PropertySource(value = "classpath:user.yml",encoding = "utf-8")
public class User {
    // @Value() 注解必须要
    @Value("${name}")
    private String name;

    @Value("${age}")
    private int age;

    @Value("${desc}")
    private String desc;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}

```
### 多环境配置

`application-dev.properties`: 开发环境  
`application-prod.properties`: 生产环境

Spring Boot 通过 `application.roperties` 文件，设置 `spring.profiles.active` 属性加载相应的文件，如：`spring.profiles.active=dev`。

注意：Spring Boot 根据环境激活配置文件的规则是，默认加载 `application.properties` 文件，当此文件配置了 `spring.profiles.active=xxx` 后，会加载 `application-xxx.properties` 文件中的配置项，并覆盖 `application.properties` 中相同的配置项。

### 最后

1. 使用 YAML 文件时，属性值和冒号中间必须要有空格。
2. YAML 文件在配置中文值时，读取不会出现乱码问题；properties 文件配置中文值，读取会出现乱码。
因为 Spring Boot 是以 `iso-8859-1` 的编码格式读取 properties 文件。











  


