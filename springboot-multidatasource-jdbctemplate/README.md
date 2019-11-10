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

- application.properties

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
spring.datasource.druid.two.password=root
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

### 编写 DAO 和 Service

上面的配置工作完成后，接下来编写数据库操作的 DAO 类和 Service 类。

**数据源一**

- 编写 DAO 类

```java
@Repository
public class UserRepository {

    @Autowired
    @Qualifier("JdbcTemplateOne") // 由于是多数据源，在注入 JdbcTemplate 时，需指明是哪个数据源的。
    private JdbcTemplate jdbcTemplate;

    public int insertUser(User user) {
        String sql = "INSERT INTO user(username, password) VALUES(?,?)";
        int count = jdbcTemplate.update(sql, user.getUsername(), user.getPassword());
        return count;

    }
}
```

- 编写 Service 类

```java
@Service
public class UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private UserRepository userRepository;

    public void insertUser(User user){
        userRepository.insertUser(user);
        logger.info("Insert user success");
    }
}
```

**数据源二**

- 编写 DAO 类

```java
@Repository
public class ProductRepository {

    @Autowired
    @Qualifier("JdbcTemplateTwo")
    private JdbcTemplate jdbcTemplate;

    public Integer insertProduct(Product product) {
        String sql = "INSERT INTO product(product_name, price, address) VALUES(?,?,?)";
        int count = jdbcTemplate.update(sql, product.getProductName(), product.getPrice(), product.getAddress());
        return count;

    }

}
```

- 编写 Service 类

```java
@Service
public class ProductService {

    private static final Logger logger = LoggerFactory.getLogger(ProductService.class);

    @Autowired
    private ProductRepository productRepository;

    public void insertProduct(Product product) {
        productRepository.insertProduct(product);
        logger.info("Insert product success");
    }
}
```

最后，详细代码可以查看本示例的 Demo。
