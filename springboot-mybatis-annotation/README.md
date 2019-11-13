## Spring Boot 集成 MyBatis（注解方式）操作数据库

注解方式相较于 XML 方式而言，SQL 语句查找方便，直接放在接口方法的注解上，可以很容易找到，但 SQL 语句排版效果不是很好，如果是复杂的 SQL 语句很难看明白它的逻辑，并且对动态 SQL 语句的支持很差，需要单独提供生成 SQL 语句的方法。

下面，我们来看看基于注解方式的 MyBatis 如何配置。

### 添加 MyBatis 依赖

```java
<dependency>
    <groupId>org.mybatis.spring.boot</groupId>
    <artifactId>mybatis-spring-boot-starter</artifactId>
    <version>1.3.1</version>
</dependency>
```

### 配置数据源信息

- application.properties

```java
spring.datasource.url=jdbc:mysql://localhost:3306/game?serverTimezone=UTC&useUnicode=true&characterEncoding=utf-8
spring.datasource.username=root
spring.datasource.password=root
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
```

### 创建 Mapper 类

```java
@Mapper // 此处可以不用添加注解，可以统一在启动类上添加 @MapperScan
public interface UserMapper {

    @Select("SELECT * FROM user WHERE username = #{username}")
    User findByName(@Param("username") String name);

    @Insert("INSERT INTO user(username, password) VALUES(#{username}, #{password})")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    int save(@Param("name") String name, @Param("password") String password);

    @UpdateProvider(type = UserDAOProvider.class, method = "updateByPrimaryKey")
    int updateById(@Param("user") User user);

    @Delete("delete from user where id = #{id}")
    int deleteById(@Param("id") Integer id);
}
```
其中，Update 方法使用的是一个 type 和一个 method 属性来指定，它们指定了 Provider 类中的一个方法。因为在进行数据更新时需要判断字段是否为空来决定是否更新这个字段，而在 XML 配置中可以使用 `<if>` 标签实现。如果使用注解的方式，就只能通过提供一个 Provider 类来动态生成 SQL 语句。

- UserDAOProvider.class

```java
public class UserDAOProvider {

    public String updateByPrimaryKey(Map<String, Object> map) {
        User user = (User) map.get("user");
        if (user == null || user.getId() == null) {
            throw new RuntimeException("The primaryKey can not be null.");
        }
        // 拼接 sql 语句
        StringBuilder updateStrSb = new StringBuilder("UPDATE user SET ");
        StringBuilder setStrSb = new StringBuilder();
        if (user.getUsername() != null) {
            setStrSb.append("username = #{user.username},");
        }
        if (user.getPassword() != null) {
            setStrSb.append("password = #{user.password}");
        }

        if (setStrSb.length() <= 0) {
            throw new RuntimeException("None element to update.");
        }
        updateStrSb.append(setStrSb).append(" WHERE id = #{user.id}");

        return updateStrSb.toString();
    }
}
```

### 在启动类上添加 @MapperScan 注解

MyBatis 启动时可以不在 mapper 层加上 @Mapper 注解，但是一定要在启动类上加上 @MapperScan 注解，并指定扫包范围。如果在 mapper 接口类上加上了 @Mapper 注解，就不需要在启动类上加上 @MapperScan 注解了。

```java
@SpringBootApplication
@MapperScan(basePackages = "com.example.mybatis.mapper")
public class MybatisApplication {

    public static void main(String[] args) {
        SpringApplication.run(MybatisApplication.class, args);
    }

}
```

这样，MyBatis 注解方式的使用配置就完成了，接下来就可以操作数据库啦！

最后，详细代码可以查看本示例的 Demo。






