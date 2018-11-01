# springboot 使用Jta-Atomikos解决分布式事务问题
Atomikos 是一个为Java平台提供增值服务的并且开源类事务管理器。  

**分布式事务问题产生原因：多数据源下的不同服务**  

### 添加jta-atomikos依赖
```java
<dependency>
	<groupId>org.springframework.boot</groupId>
	<artifactId>spring-boot-starter-jta-atomikos</artifactId>
</dependency>
```
### application.properties文件中配置数据源
使用的数据库是MySQL，驱动是6.0以上的，会由于数据库和系统时区差异产生异常，在jdbc连接的url后面加上`serverTimezone=GMT`或`serverTimezone=UTC`即可解决问题，如果需要使用gmt+8时区，需要写成`GMT%2B8`，否则会被解析为空。再一个解决办法就是使用低版本的mysql jdbc驱动，5.1.28不会存在时区的问题。

```java
# Mysql 1
mysql.datasource.test1.url = jdbc:mysql://localhost:3306/game?serverTimezone=UTC&useUnicode=true&characterEncoding=utf-8
mysql.datasource.test1.username = root
mysql.datasource.test1.password = root

mysql.datasource.test1.minPoolSize = 3
mysql.datasource.test1.maxPoolSize = 25
mysql.datasource.test1.maxLifetime = 20000
mysql.datasource.test1.borrowConnectionTimeout = 30
mysql.datasource.test1.loginTimeout = 30
mysql.datasource.test1.maintenanceInterval = 60
mysql.datasource.test1.maxIdleTime = 60

# Mysql 2
mysql.datasource.test2.url =jdbc:mysql://localhost:3306/bookstore?serverTimezone=UTC&useUnicode=true&characterEncoding=utf-8
mysql.datasource.test2.username =root
mysql.datasource.test2.password =root

mysql.datasource.test2.minPoolSize = 3
mysql.datasource.test2.maxPoolSize = 25
mysql.datasource.test2.maxLifetime = 20000
mysql.datasource.test2.borrowConnectionTimeout = 30
mysql.datasource.test2.loginTimeout = 30
mysql.datasource.test2.maintenanceInterval = 60
mysql.datasource.test2.maxIdleTime = 60
```
### 读取配置文件信息
将配置文件中的不同数据源信息，注入不同实体类。
```java
/**
 * 数据源配置
 * @author kevin
 **/
@Configuration
@ConfigurationProperties(prefix = "mysql.datasource.test1")
public class DataSourceConfiguration1 {

    private String url;

    private String username;

    private String password;

    private int minPoolSize;

    private int maxPoolSize;

    private int maxLifetime;

    private int borrowConnectionTimeout;

    private int loginTimeout;

    private int maintenanceInterval;

    private int maxIdleTime;

    private String testQuery;

    getter和setter方法.....
}
```

### 创建多数据源
```java
@Configuration
@MapperScan(basePackages = "com.example.jtaatomikos.datasource1", sqlSessionTemplateRef = "sqlSessionTemplate1")
public class MyBatisConfig1 {

    /**
     *  配置数据源
     */
    @Primary
    @Bean(name = "dataSource1")
    public DataSource testDataSource(DataSourceConfiguration1 testConfig) throws SQLException {
        MysqlXADataSource mysqlXaDataSource = new MysqlXADataSource();
        mysqlXaDataSource.setUrl(testConfig.getUrl());
        mysqlXaDataSource.setPinGlobalTxToPhysicalConnection(true);
        mysqlXaDataSource.setPassword(testConfig.getPassword());
        mysqlXaDataSource.setUser(testConfig.getUsername());
        mysqlXaDataSource.setPinGlobalTxToPhysicalConnection(true);

        // 将本地事务注册到Atomikos全局事务
        AtomikosDataSourceBean xaDataSource = new AtomikosDataSourceBean();
        xaDataSource.setXaDataSource(mysqlXaDataSource);
        xaDataSource.setUniqueResourceName("dataSource1");

        xaDataSource.setMinPoolSize(testConfig.getMinPoolSize());
        xaDataSource.setMaxPoolSize(testConfig.getMaxPoolSize());
        xaDataSource.setMaxLifetime(testConfig.getMaxLifetime());
        xaDataSource.setBorrowConnectionTimeout(testConfig.getBorrowConnectionTimeout());
        xaDataSource.setLoginTimeout(testConfig.getLoginTimeout());
        xaDataSource.setMaintenanceInterval(testConfig.getMaintenanceInterval());
        xaDataSource.setMaxIdleTime(testConfig.getMaxIdleTime());
        xaDataSource.setTestQuery(testConfig.getTestQuery());
        return xaDataSource;
    }

    @Primary
    @Bean(name = "sqlSessionFactory1")
    public SqlSessionFactory testSqlSessionFactory(@Qualifier("dataSource1") DataSource dataSource)
            throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource);
        return bean.getObject();
    }

    @Primary
    @Bean(name = "sqlSessionTemplate1")
    public SqlSessionTemplate testSqlSessionTemplate(
            @Qualifier("sqlSessionFactory1") SqlSessionFactory sqlSessionFactory) throws Exception {
        return new SqlSessionTemplate(sqlSessionFactory);
    }
}
```
其他数据源配置相同，但是需要使用`@primary`注解指定主数据源。 

在使用分布式事务管理后，使用`@Transactional`注解时不需要指定事务管理器。





