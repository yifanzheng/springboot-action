## Spring Boot 集成 MyBatis（注解方式）实现多数据源配置

MyBatis 注解方式配置多数据源与 XML 方式大同小异，唯一不同是注解方式不用指定 XML 文件的位置。

### 配置数据源信息

- application.properties

```java
# 数据源一
spring.datasource.resource1.driver-class-name=com.mysql.jdbc.Driver
# 注意区分 spring.datasource.url 与自定义jdbc配置的 jdbc-url
spring.datasource.resource1.jdbc-url=jdbc:mysql://localhost:3306/game?useUnicode=true&characterEncoding=utf-8
spring.datasource.resource1.username=root
spring.datasource.resource1.password=root

# 数据源二
spring.datasource.resource2.driver-class-name=com.mysql.jdbc.Driver
spring.datasource.resource2.jdbc-url=jdbc:mysql://localhost:3306/bookstore?useUnicode=true&characterEncoding=utf-8
spring.datasource.resource2.username=root
spring.datasource.resource2.password=root
```
### 配置多数据源

集成 MyBatis 配置数据源步骤如下：

- 创建数据源 `Datasource`
- 创建 Session 会话工厂 `SqlSessionFactroy`
- 创建数据库事务 `DataSourceTransactionManager`
- 创建 `SqlSessionTemplate`

最关键的地方就是数据源配置这部分，需要一层一层注入,先创建`DataSource`，再创建`SqlSessionFactory`，然后再创建事务管理器`DataSourceTransactionManager`，最后包装到`SqlSessionTemplate`中。

**数据源一**

```java
@Configuration
@MapperScan(basePackages = "com.example.mybatis.mapper.primary",
        sqlSessionFactoryRef = "PrimarySqlSessionFactory")
public class PrimaryDataSourceConfig {

    /**
     * 配置数据源
     *
     * @return
     */
    @Bean(name = "PrimaryDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.primary")
    public DataSource dataSource() {
        return DataSourceBuilder.create().build();
    }

    /**
     * 配置 sql 会话工厂
     */
    @Bean(name = "PrimarySqlSessionFactory")
    public SqlSessionFactory sessionFactory(@Qualifier("PrimaryDataSource") DataSource dataSource) throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource);

        return bean.getObject();
    }

    /**
     * 事务管理器
     */
    @Bean(name = "PrimaryTransactionManager")
    public DataSourceTransactionManager transactionManager(@Qualifier("PrimaryDataSource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean(name = "PrimarySqlSessionTemplate")
    public SqlSessionTemplate sqlSessionTemplate(@Qualifier("PrimarySqlSessionFactory") SqlSessionFactory sessionFactory) {
        return new SqlSessionTemplate(sessionFactory);
    }
}
```

**数据源二**

```java
@Configuration
@MapperScan(basePackages = "com.example.mybatis.mapper.secondary",
        sqlSessionFactoryRef = "SecondarySqlSessionFactory")
public class SecondaryDataSourceConfig {

    /**
     * 配置数据源
     */
    @Bean(name = "SecondaryDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.secondary")
    public DataSource dataSource() {
        return DataSourceBuilder.create().build();
    }

    /**
     * 配置 sql 会话工厂
     */
    @Bean(name = "SecondarySqlSessionFactory")
    public SqlSessionFactory sessionFactory(@Qualifier("SecondaryDataSource") DataSource dataSource) throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource);

        return bean.getObject();
    }

    /**
     * 事务管理器
     */
    @Bean(name = "SecondaryTransactionManager")
    public DataSourceTransactionManager transactionManager(@Qualifier("SecondaryDataSource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean(name = "SecondarySqlSessionTemplate")
    public SqlSessionTemplate sqlSessionTemplate(@Qualifier("SecondarySqlSessionFactory") SqlSessionFactory sessionFactory) {
        return new SqlSessionTemplate(sessionFactory);
    }
}
```
注意：在 springboot 2.x 之前，需要使用注解 `@primary` 指定主库，否则会报错。

最后，详细代码可以查看本示例的 Demo。



