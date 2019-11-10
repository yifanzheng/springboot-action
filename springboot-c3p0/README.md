## Spring Boot 整合 C3P0 数据库连接池

本示例使用 C3P0 + DbUtils + SQLServer 进行整合来操作数据库。

### 配置 C3P0 信息

- application.properties

```properties
# SQLServer 数据库配置信息
c3p0.jdbcUrl=jdbc:sqlserver://localhost:1433;DatabaseName=game
c3p0.user=gm
c3p0.password=root
c3p0.driverClass=com.microsoft.sqlserver.jdbc.SQLServerDriver
c3p0.minPoolSize=2
c3p0.maxPoolSize=10
c3p0.maxIdleTime=30
c3p0.checkoutTimeout=30000
c3p0.acquireIncrement=3
c3p0.maxStatements=1000
c3p0.initialPoolSize=3
c3p0.idleConnectionTestPeriod=60
c3p0.acquireRetryAttempts=30
c3p0.acquireRetryDelay=1000
c3p0.breakOnAcquireFailure=true
c3p0.breakAfterAcquireFailure=false
c3p0.testConnectionOnCheckout=false
```
### 引入 C3P0 和 SQLServer 依赖

```xml
<dependency>
    <groupId>c3p0</groupId>
    <artifactId>c3p0</artifactId>
    <version>0.9.1.2</version>
</dependency>

<dependency>
    <groupId>com.microsoft.sqlserver</groupId>
    <artifactId>mssql-jdbc</artifactId>
    <version>7.0.0.jre8</version>
</dependency>
<!-- 引入 dbutils 依赖操作数据库 -->
<dependency>
    <groupId>commons-dbutils</groupId>
    <artifactId>commons-dbutils</artifactId>
    <version>1.6</version>
</dependency>
``` 
### 创建 C3P0 配置类

```java
@Configuration
public class C3p0Configuration {

    @Bean(name = "dataSource")
    @Primary // 用 @Primary 区分主数据源
    @ConfigurationProperties(prefix = "c3p0") // 指定配置文件中，前缀为 c3p0 的属性值
    public DataSource dataSource(){
        return DataSourceBuilder.create()
                .type(ComboPooledDataSource.class).build();
    }
}
```
### 创建 QueryRunner 对象

QueryRunner 中提供了对 SQL 语句操作的 API，不用我们手动写操作数据库的代码，同时也无需编写任何清理或资源泄漏防护代码。

```java
@Configuration
public class QueryRunnerConfiguration {

    @Autowired
    private DataSource dataSource;

    @Bean(name = "queryRunner")
    public QueryRunner queryRunner(){
        // 使用 SQlServer 数据库时，pmdKnownBroken 要设置为 true，否则在预编译时的参数无法自动传入
        return new QueryRunner(dataSource,true);
    }
}
```

### 注意

在使用 DbUtils 组件进行数据库操作时，如果是操作 SQLServer 数据库，在构建 QueryRunner 对象时，pmdKnownBroken 要设置为 true，否则在预编译时的参数无法自动传入。

```java
new QueryRunner(dataSource,true);
```
