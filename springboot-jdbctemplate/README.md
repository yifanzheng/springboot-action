## Spring Boot 集成 JdbcTemplate 操作数据库

本示例使用 Spring Boot 集成 Druid 数据库连接池和 JdbcTemplate 操作数据库，包括对数据的增删改查操作。

### 添加相关依赖

添加 `spring-boot-starter-jdbc` 依赖、MySQL 依赖、Druid 数据库连接池依赖。

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

### 配置数据源

**添加配置信息**

- application.properties

```properties
# 数据源
spring.datasource.druid.driver-class-name=com.mysql.jdbc.Driver
spring.datasource.druid.url=jdbc:mysql://localhost:3306/game?useSSL=false&useUnicode=true&characterEncoding=utf-8&allowMultiQueries=true
spring.datasource.druid.username=root
spring.datasource.druid.password=root
# 配置阿里巴巴提供的 Druid 数据库连接池
spring.datasource.druid.type=com.alibaba.druid.pool.DruidDataSource
spring.datasource.druid.initial-size=1
spring.datasource.druid.min-idle=1
spring.datasource.druidmax-active=20
spring.datasource.druid.test-on-borrow=true
spring.datasource.druid.stat-view-servlet.allow=true
```

**创建 DataSource 和 JdbcTemplate**

```java
@Configuration
public class DatabaseConfig {

    @Bean
    @ConfigurationProperties(prefix = "spring.datasource.druid")
    public DataSource dataSource(){
        return new DruidDataSource();
    }

    @Bean
    public JdbcTemplate jdbcTemplate() {
        // 这里要将配置的 DruidDataSource 数据源注入 JdbcTemplate 中，不然默认注入 Spring Boot 自带的 HikariDatasource。
        return new JdbcTemplate(dataSource());
    }
}
```

### 使用 JdbcTemplate 操作数据库

**User 实体类**

实体类的字段名要和数据库的字段名一一对应。

```java
public class User {

    private Long id;

    private String username;

    private String password;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
```

**DAO 层**

```java
@Repository
public class UserRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public User getUser(String username) {
        String sql = "SELECT id, username, password FROM user WHERE username = ?";
        Map<String, Object> resultMap = jdbcTemplate.queryForMap(sql, username);
        User user = new User();
        user.setId(DataConvertUtils.getLong(resultMap.get("id")));
        user.setUsername(DataConvertUtils.getString(resultMap.get("username")));
        user.setPassword(DataConvertUtils.getString(resultMap.get("password")));

        return user;
    }

    public List<User> listUser() {
        String sql = "SELECT id, username, password FROM user";
        List<Map<String, Object>> maps = jdbcTemplate.queryForList(sql);
        List<User> users = maps.stream().map(e -> {
            User user = new User();
            user.setId(DataConvertUtils.getLong(e.get("id")));
            user.setUsername(DataConvertUtils.getString(e.get("username")));
            user.setPassword(DataConvertUtils.getString(e.get("password")));
            return user;
        }).collect(Collectors.toList());
        return users;
    }

    /**
     * 不存在则插入，存在则更新
     * 注：被插入的数据中需要存在 UNIQUE 索引或 PRIMARY KEY 字段，这里使用 username 字段作为唯一索引 (UNIQUE)
     *
     * @param user
     */
    public int saveOrUpdateUser(User user) {
        String sql = "INSERT INTO user(username, password) VALUES(?,?)" +
                " ON DUPLICATE KEY" +
                " UPDATE password = ?";
        int count = jdbcTemplate.update(sql, user.getUsername(), user.getPassword(), user.getPassword());

        return count;
    }

    public int deleteUser(String username) {
        String sql = "DELETE FROM user WHERE username = ?";
        int count = jdbcTemplate.update(sql, username);

        return count;
    }
}
```

**Service 层**

```java
@Service
public class UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private UserRepository userRepository;

    public User getUser(String username) {
        User user = userRepository.getUser(username);

        return user;
    }

    public List<User> listUser() {
        List<User> users = userRepository.listUser();

        return users;
    }

    public void saveOrUpdateUser(User user) {
        userRepository.saveOrUpdateUser(user);
        logger.debug("Update user success");
    }

    public void deleteUser(String username) {
        userRepository.deleteUser(username);
        logger.debug("Delete user for username: {}", username);
    }
}
```