## Spring Boot 集成 MyBatis（XML 方式）实现多数据源配置

### 添加相关依赖

```xml
<dependency>
    <groupId>org.mybatis.spring.boot</groupId>
    <artifactId>mybatis-spring-boot-starter</artifactId>
    <version>1.3.1</version>
</dependency>
```

### 配置数据源信息

- application.properties

```properties
# 数据源一
spring.datasource.primary.jdbc-url=jdbc:mysql://localhost:3306/game?serverTimezone=UTC&useUnicode=true&characterEncoding=utf-8
spring.datasource.primary.username=root
spring.datasource.primary.password=root
spring.datasource.primary.driver-class-name=com.mysql.jdbc.Driver

# 数据源二
spring.datasource.secondary.jdbc-url=jdbc:mysql://localhost:3306/product?serverTimezone=UTC&useUnicode=true&characterEncoding=utf-8
spring.datasource.secondary.username=root
spring.datasource.secondary.password=root
spring.datasource.secondary.driver-class-name=com.mysql.jdbc.Driver
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
@MapperScan(basePackages = "com.example.mybatis.mapper.primary", sqlSessionTemplateRef = "PrimarySqlSessionTemplate")
public class DataSourcePrimaryConfig {

    @Bean(name = "PrimaryDataSource")
    @Primary
    @ConfigurationProperties(prefix = "spring.datasource.primary")
    public DataSource dataSourcePrimary() {
       return new DruidDataSource();
    }

    @Bean(name = "PrimarySqlSessionFactory")
    @Primary
    public SqlSessionFactory sqlSessionFactoryPrimary(@Qualifier("PrimaryDataSource") DataSource dataSource) throws Exception {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dataSource);
        sqlSessionFactoryBean.setMapperLocations(new PathMatchingResourcePatternResolver()
                .getResources("classpath:mybatis/mapper/primary/*.xml"));

        return sqlSessionFactoryBean.getObject();
    }

    @Bean(name = "PrimaryTransactionManager")
    @Primary
    public DataSourceTransactionManager transactionManagerPrimary(@Qualifier("PrimaryDataSource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean(name = "PrimarySqlSessionTemplate")
    @Primary
    public SqlSessionTemplate sqlSessionTemplatePrimary(@Qualifier("PrimarySqlSessionFactory") SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }

}
```

**数据源二**

```java
@Configuration
@MapperScan(basePackages = "com.example.mybatis.mapper.secondary", sqlSessionTemplateRef = "SecondarySqlSessionTemplate")
public class DataSourceSecondaryConfig {

    @Bean(name = "SecondaryDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.secondary")
    public DataSource dataSource() {
        return new DruidDataSource();
    }

    @Bean(name = "SecondarySqlSessionFactory")
    public SqlSessionFactory sqlSessionFactorySecondary(@Qualifier("SecondaryDataSource") DataSource dataSource) throws Exception {
        SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
        factoryBean.setDataSource(dataSource);
        factoryBean.setMapperLocations(new PathMatchingResourcePatternResolver()
                .getResources("classpath:mybatis/mapper/secondary/*.xml"));

        return factoryBean.getObject();

    }

    @Bean(name = "SecondaryTransactionManager")
    public DataSourceTransactionManager transactionManagerSecondary(@Qualifier("SecondaryDataSource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean(name = "SecondarySqlSessionTemplate")
    public SqlSessionTemplate sqlSessionTemplateSecondary(@Qualifier("SecondarySqlSessionFactory") SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }
}
```
以上配置完成之后，集成 MyBatis 多数据源配置就已经完成了，接下来就可以操作数据啦！

最后，详细代码可以查看本示例的 Demo。


