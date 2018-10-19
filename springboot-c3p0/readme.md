# SpringBoot 整合 C3P0 数据库连接池

#### 在application.properties文件中配置c3p0参数
```java
# SQLServer数据库
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
c3p0.breakAfterAcquireFailure=false
c3p0.testConnectionOnCheckout=false
```
#### 在pom.xml文件中引入c3p0,sqlserver依赖
```java
<dependency>
    <groupId>c3p0</groupId>
    <artifactId>c3p0</artifactId>
    <version>0.9.1.2</version>
</dependency>

<dependency>
    <groupId>com.microsoft.sqlserver</groupId>
    <artifactId>sqljdbc4</artifactId>
    <version>4.0</version>
</dependency>

其中，sqljdbc4需要下载好jar包后可以通过下面的maven命令将jar包安装到自己的私服上。

mvn install:install-file -Dfile=sqljdbc4.jar -Dpackaging=jar -DgroupId=com.microsoft.sqlserver -DartifactId=sqljdbc4 -Dversion=4.0

安装完成后才可以在pom.xml文件中引入依赖。
``` 
#### 创建c3p0配置类，注入配置
```java
@Configuration
public class C3p0Configuration {

    @Bean(name = "dataSource")
    @Qualifier(value = "dataSource") // 限定描述符
    @Primary // 用@Primary区分主数据源
    @ConfigurationProperties(prefix = "c3p0") // 指定配置文件中，前缀为c3p0的属性值
    public DataSource dataSource(){
        return DataSourceBuilder.create()
                .type(ComboPooledDataSource.class).build();
    }
}
```
#### 注意
在使用dbutils组件进行数据库操作时，如果是操作SQlServer数据库，在构建QueryRunner对象时pmdKnownBroken要设置为true，否则在预编译时的参数无法自动传入。
```java
new QueryRunner(dataSource,true);
```
