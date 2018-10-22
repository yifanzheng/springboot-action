# springboot 整合 mybatis 实现数据访问
### pom.xml文件中添加mybatis依赖
```java
<dependency>
    <groupId>org.mybatis.spring.boot</groupId>
    <artifactId>mybatis-spring-boot-starter</artifactId>
    <version>1.1.1</version>
</dependency>
```
### 在application.properties引入相关数据库配置
```java
spring.datasource.url=jdbc:mysql://localhost:3306/game
spring.datasource.username=root
spring.datasource.password=root
spring.datasource.driver-class-name=com.mysql.jdbc.Driver
```
### 创建Mapper类
```java
@Mapper // 此处可以不用添加注解，可以统一在启动类上添加@MapperScan
public interface UserMapper {

    @Select("SELECT * FROM user WHERE NAME = #{name}")
    Users findByName(@Param("name") String name);

    @Insert("INSERT INTO user(NAME, password) VALUES(#{name}, #{password})")
    int insert(@Param("name") String name, @Param("password") String password);
}
```
### 在启动类上添加@MapperScan注解
```java
/**
 * mybatis启动时可以不在mapper层加@Mapper注解，但是一定要在启动类上加上@MapperScan注解，并指定扫包范围。
 * 如果在mapper接口类上加上了@Mapper注解，就不需要在启动类上加@MapperScan注解了。
 */
@SpringBootApplication
@MapperScan(basePackages = "com.example.mybatis.mapper")
public class MybatisApplication {

	public static void main(String[] args) {
		SpringApplication.run(MybatisApplication.class, args);
	}
}
```
### 使用@Transactional进行事务管理
##### 1. Spring事务的分类
- 声明式事务

      编程式事务+AOP技术包装，使用注解进行扫包，指定范围进行事务管理。
- 编程式事务

      在代码中显式调用开启事务、提交事务、回滚事务的相关方法。
##### 2. spring事务的原理
使用AOP **环绕通知** 和 **异常通知**。
注意： 在使用spring事务时不能使用`try-catch`进行异常捕获，要将异常抛给外层，使其进行异常拦截，触发事务机制。

##### 3. 在方法上添加@Transaction注解
```java
 /**
  * 添加用户数据
  */
@Transactional(rollbackFor = Exception.class,propagation = Propagation.REQUIRED) // 进行事务管理
public int insertUser(String name,String password){
    int insert = mapper.insert(name, password);
    int a=1/0;
    return insert;
}
```
##### 4. 事务的传播行为
所谓事务的传播行为，是指如果在开始当前事务之前，一个事务上下文已经存在，此时有若干选项可以指定一个事务性方法的执行行为。

**`REQUIRED`**：如果当前存在事务，则加入该事务；如果当前没有事务，则创建一个新的事务。这是默认值。
**`REQUIRES_NEW`**：创建一个新的事务，如果当前存在事务，则把当前事务挂起。
**`SUPPORTS`**：如果当前存在事务，则加入该事务；如果当前没有事务，则以非事务的方式继续运行。
**`NOT_SUPPORTED`**：以非事务方式运行，如果当前存在事务，则把当前事务挂起。
**`NEVER`**：以非事务方式运行，如果当前存在事务，则抛出异常。
**`MANDATORY`**：如果当前存在事务，则加入该事务；如果当前没有事务，则抛出异常。
**`NESTED`**：如果当前存在事务，则创建一个事务作为当前事务的嵌套事务来运行；如果当前没有事务，则该取值等价于 **`REQUIRED`**。


