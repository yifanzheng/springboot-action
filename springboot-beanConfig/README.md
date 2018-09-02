# SpringBoot 资源文件属性配置 #

### 优先级 ###

    1. 命令行参数。
    2. 从 java:comp/env 得到的 JNDI 属性。
    3. 通过 System.getProperties() 获取的 Java 系统参数。
    4. 操作系统环境变量。
    5. RandomValuePropertySource配置的random.*属性值。
      ${random.int}、${random.long}、${random.value}、${random.uuid}等。

    6. jar包外部的application-{profile}.properties或application.yml(带spring.profile)配置文件。
    7. jar包内部的application-{profile}.properties或application.yml(带spring.profile)配置文件。 
    8. jar包外部的application.properties或application.yml(不带spring.profile)配置文件。
    9. jar包内部的application.properties或application.yml(不带spring.profile)配置文件。
    （从jar包外向jar包内进行寻找，优先加载带{profile}的，再加载不带{profile的}） 

    10. @Configuration注解类上的@PropertySource。
    11. 通过SpringApplication.setDefaultProperties指定默认属性。

    注意：application.properties的优先级高于application.yml文件的优先级。


### 资源文件中的属性配置与映射到实体类

首先在pom.xml文件中，加入依赖项：
```java
  <!--加入该依赖，才能用于读取配置值-->
        <dependency>
		<groupId>org.springframework.boot</groupId>
		<artifactId>spring-boot-configuration-processor</artifactId>
		<optional>true</optional>
        </dependency>
```
- 在`@Configuration`注解的类上加上`@ConfigurationProperties(prefix="前缀名")`注解，可以使用`@PropertySource`注解指定加载的配置文件，不加时默认加载`application.properties`文件。

 ```java
    @Configuration
    @ConfigurationProperties(prefix = "com.resource")
    //指定加载的资源文件
    @PropertySource(value = "classpath:resource.properties") 
    public class ResourceConfig {
        private String name;

        private String website;

        private String language;

        get和set方法...
    }

    注意：使用这种方式配置的类，在使用 @Autowired 注入时，不能直接return注入的对象，
    它只是指向spring容器中resourceConfig对象资源的一个标识，可以通过这个标识返回该
    对象中的值。
 ```
- 使用`@Value`注解,直接映射实体类的各个属性
 ```java
    @Component
    @PropertySource(value = "classpath:resource.properties")
    public class ResourceComponent {
        @Value("${com.resource.name}")
        private String name;

        @Value("${com.resource.website}")
        private String website;

        @Value("${com.resource.language}")
        private String language;

        get和set方法...
    }

 ```
- 在方法上使用`@Bean`注解，配合以上注解使用
```java
    @Configuration
    @PropertySource(value = "classpath:resource.properties")
    public class ResourceBeanConfig {

        @Bean
        @ConfigurationProperties(prefix = "com.resource")
        public ResourceBean getResourceBean(){
             return new ResourceBean();
        }
    }
```
在没有使用`@PropertySource`注解指定加载的文件时，默认使用`application.properties`文件中的与实体对象的属性。
`@PropertySources`注解优先级比较低，即使指定了加载的文件，但出现与`application.properties`相同的配置项时会被其覆盖。
### 自定义`.yml`资源文件属性配置
- 创建`user.yml`文件，进行属性配置
```java
#配置user对象的值
demo:
 user:
  name: 魏婴
  age: 12
  desc: Hi all,my name is 魏无羡
```
- 注释`User.java`对象文件
```java
@Configuration
@ConfigurationProperties(prefix = "demo.user") //前缀名注释必须有，不然会报错
@PropertySource(value = "classpath:user.yml",encoding = "utf-8")
public class User {
    @Value("${name}")//这个注释必须要
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
### 多环境配置（数据库配置，Redis配置，日志配置）

`application-dev.properties`: 开发环境  
`application-prod.properties`: 生产环境

springboot通过`application.roperties`文件，设置`spring.profiles.active`属性加载相应的文件，如：`spring.profiles.active=dev`。

注意：springboot根据环境激活配置文件的规则是，默认加载`application.properties`文件，当此文件配置了`spring.profiles.active=xxx`后，会加载`application-xxx.properties`文件中的配置项，并覆盖`application.properties`中相同的配置项。

### 最后

1. 使用`.yml`文件时，属性值和冒号中间必须要有空格。
2. `.yml`文件在配置中文值时，读取不会出现乱码问题；`.properties`文件配置中文值，读取会出现乱码。
因为springboot是以`iso-8859-1`的编码格式读取`.properties`文件。











  


