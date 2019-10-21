# springboot 使用分包名（业务）的方式整合多数据源
数据源可以根据 **分包名（业务）** 或 **注解方式** 来划分

### application.properties配置
- 配置不同数据源的连接地址
```java
# 数据源1
spring.datasource.resource1.driver-class-name=com.mysql.jdbc.Driver
# 注意区分 spring.datasource.url 与自定义jdbc配置的 jdbc-url
spring.datasource.resource1.jdbc-url=jdbc:mysql://localhost:3306/game?useUnicode=true&characterEncoding=utf-8
spring.datasource.resource1.username=root
spring.datasource.resource1.password=root

# 数据源2
spring.datasource.resource2.driver-class-name=com.mysql.jdbc.Driver
spring.datasource.resource2.jdbc-url=jdbc:mysql://localhost:3306/bookstore?useUnicode=true&characterEncoding=utf-8
spring.datasource.resource2.username=root
spring.datasource.resource2.password=root
```
### 数据源配置
- 最关键的地方就是这块了，一层一层注入,先创建`DataSource`，再创建`SqlSessionFactory`，然后再创建事务管理器`DataSourceTransactionManager`，最后包装到`SqlSessionTemplate`中
```java
/**
 * 数据源1配置
 *
 * @author kevin
 **/
@Configuration
// 指明了扫描dao层，并且给dao层注入指定的SqlSessionTemplate。所有@Bean都需要按照命名指定正确
@MapperScan(basePackages = "com.example.resource.datasource01", sqlSessionFactoryRef = "sqlSessionFactory1")
public class DataSource1Configuration {

    /**
     * 配置数据源
     *
     * @return
     */
    @Bean(name = "datasource1")
    @ConfigurationProperties(prefix = "spring.datasource.resource1")
    public DataSource dataSource() {
        return DataSourceBuilder.create().build();
    }

    /**
     * 配置sql会话工厂
     *
     * @param dataSource
     * @return
     * @throws Exception
     */
    @Bean(name = "sqlSessionFactory1")
    public SqlSessionFactory sessionFactory(@Qualifier("datasource1") DataSource dataSource) throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource);
        // 加载mapper配置文件,如果使用了的话
        // bean.setMapperLocations(
        // new
        // PathMatchingResourcePatternResolver().getResources("classpath:mapper/test2/*.xml"));

        return bean.getObject();
    }

    /**
     * 事务管理器
     * @param dataSource
     * @return
     */
    @Bean(name = "transactionManager1")
    public DataSourceTransactionManager transactionManager(@Qualifier("datasource1") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean(name = "SqlSessionTemplate1")
    public SqlSessionTemplate sqlSessionTemplate(@Qualifier("sqlSessionFactory1") SqlSessionFactory sessionFactory) {
        return new SqlSessionTemplate(sessionFactory);
    }
}
```
注意：在 springboot 2.x之前，需要使用注解`@primary`指定主库，否则会报错。

### 多数据源的事务管理
在多数据源情况下，事务需要指定事务管理器以区分不同的数据源事务。
```java
@Transactional(rollbackFor = Exception.class, transactionManager = "transactionManager1")
```
