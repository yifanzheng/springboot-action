## Spring Boot 集成 JdbcTemplate 实现多数据源配置

在实际开发中，我们会遇到一个应用需要访问多个数据源的情况。因此，我们需要配置多个数据源。使用 JdbcTemplate 实现多数据源配置是比较简单的。

### 添加相关依赖

```xml
<dependency>
  <groupId>org.springframework.boot</groupId>
  <artifactId>spring-boot-starter-jdbc</artifactId>
</dependency>
<dependency>
  <groupId>mysql</groupId>
  <artifactId>mysql-connector-java</artifactId>
  <version>5.1.35</version>
</dependency>
<dependency>
  <groupId>com.alibaba</groupId>
  <artifactId>druid</artifactId>
  <version>1.0.14</version>
</dependency>
```

### 配置数据源信息

```properties
# 数据源一
spring.datasource.druid.one.driver-class-name=com.mysql.jdbc.Driver
spring.datasource.druid.one.url=jdbc:mysql://localhost:3306/game?useUnicode=true&characterEncoding=utf-8
spring.datasource.druid.one.username=root
spring.datasource.druid.one.password=root

# 数据源二
spring.datasource.druid.two.driver-class-name=com.mysql.jdbc.Driver
spring.datasource.druid.two.url=jdbc:mysql://localhost:3306/product?useUnicode=true&characterEncoding=utf-8
spring.datasource.druid.two.username=root
spring.datasource.druid.two.password=rootm
```

### 创建 DataSource 和 JdbcTemplate

**数据源一**

这里将数据源一作为主数据源，并添加 `@Primary` 注解。

```java
@Configuration
public class DataSourceOneConfig {

    @Bean(name = "DataSourceOne")
    @Primary
    @ConfigurationProperties(prefix = "spring.datasource.druid.one")
    public DataSource dataSource() {
        return new DruidDataSource();
    }

    @Bean(name = "JdbcTemplateOne")
    @Primary
    public JdbcTemplate jdbcTemplate(@Qualifier("DataSourceOne") DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

}
```

**数据源二**

```java
@Configuration
public class DataSourceTwoConfig {

    @Bean(name = "DataSourceTwo")
    @ConfigurationProperties(prefix = "spring.datasource.druid.two")
    public DataSource dataSource() {
        return new DruidDataSource();
    }

    @Bean(name = "JdbcTemplateTwo")
    public JdbcTemplate jdbcTemplate(@Qualifier("DataSourceTwo") DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }
}
```

最后，详细代码可以查看 Demo。