## Spring Boot 集成 JPA 实现多数据源配置

### 添加 JPA 依赖

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-jpa</artifactId>
</dependency>
```

### 配置数据源信息

- application.properties

```properties
# 数据源一
spring.datasource.primary.jdbc-url=jdbc:mysql://localhost:3306/product?autoReconnect=true&useUnicode=true&characterEncoding=utf-8&serverTimezone=UTC
spring.datasource.primary.diver-class-name=com.mysql.jdbc.Driver
spring.datasource.primary.username=root
spring.datasource.primary.password=root

# 数据源二
spring.datasource.secondary.jdbc-url=jdbc:mysql://localhost:3306/game?autoReconnect=true&useUnicode=true&characterEncoding=utf-8&serverTimezone=UTC
spring.datasource.secondary.diver-class-name=com.mysql.jdbc.Driver
spring.datasource.secondary.username=root
spring.datasource.secondary.password=root

# JPA 配置
spring.jpa.database=mysql
spring.jpa.generate-ddl=true
spring.jpa.show-sql=true
spring.jpa.open-in-view=false
spring.jpa.hibernate.ddl-auto=update
spring.jpa.hibernate.naming.physical-strategy=org.springframework.boot.orm.jpa.hibernate.SpringPhysicalNamingStrategy
spring.jpa.hibernate.naming.implicit-strategy=org.springframework.boot.orm.jpa.hibernate.SpringImplicitNamingStrategy
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQL5Dialect
```
### 配置多数据源

集成 JPA 配置数据源步骤如下：

- 创建数据源 `DataSource`
- 创建实体管理器工厂对象 `LocalContainerEntityManagerFactoryBean`
- 创建实体管理器对象 `EntityManager`
- 创建事务管理器对象 `PlatformTransactionManager`

**数据源一**

```java
@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        entityManagerFactoryRef = "entityManagerFactoryPrimary",
        transactionManagerRef = "transactionManagerPrimary",
        basePackages = {"com.example.jpa.repository.primary"})
public class PrimaryDataSourceConfig {

    @Autowired
    private JpaProperties jpaProperties;

    @Autowired
    private HibernateProperties hibernateProperties;

    @Bean(name = "primaryDataSource")
    @Primary
    @ConfigurationProperties(prefix = "spring.datasource.primary")
    public DataSource dataSource() {
        return DataSourceBuilder.create().build();
    }

    private Map<String, Object> getVendorProperties() {
        return hibernateProperties.determineHibernateProperties(jpaProperties.getProperties(), new HibernateSettings());
    }

    @Bean(name = "entityManagerFactoryPrimary")
    @Primary
    public LocalContainerEntityManagerFactoryBean entityManagerFactoryPrimary(EntityManagerFactoryBuilder builder) {
        return builder.dataSource(dataSource())
                .properties(getVendorProperties())
                // 设置映射的实体类位置
                .packages("com.example.jpa.entity.primary")
                .persistenceUnit("primaryPersistenceUnit")
                .build();
    }

    @Bean(name = "entityManagerPrimary")
    @Primary
    public EntityManager entityManager(EntityManagerFactoryBuilder builder){
        return entityManagerFactoryPrimary(builder).getObject().createEntityManager();
    }

    @Bean(name = "transactionManagerPrimary")
    @Primary
    public PlatformTransactionManager transactionManagerSecondary(EntityManagerFactoryBuilder builder) {
        return new JpaTransactionManager(entityManagerFactoryPrimary(builder).getObject());
    }
}
```

**数据源二**

```java
@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        entityManagerFactoryRef = "entityManagerFactorySecondary",
        transactionManagerRef = "transactionManagerSecondary",
        basePackages = {"com.example.jpa.repository.secondary"})
public class SecondaryDataSourceConfig {

    @Autowired
    private JpaProperties jpaProperties;

    @Autowired
    private HibernateProperties hibernateProperties;

    @Bean(name = "secondaryDatasource")
    @ConfigurationProperties(prefix = "spring.datasource.secondary")
    public DataSource dataSource() {
        return DataSourceBuilder.create().build();
    }

    private Map<String, Object> getVendorProperties() {
        return hibernateProperties.determineHibernateProperties(jpaProperties.getProperties(), new HibernateSettings());
    }

    @Bean(name = "entityManagerFactorySecondary")
    public LocalContainerEntityManagerFactoryBean entityManagerFactorySecondary(EntityManagerFactoryBuilder builder) {
        return builder.dataSource(dataSource())
                .properties(getVendorProperties())
                // 设置映射的实体类位置
                .packages("com.example.jpa.entity.secondary")
                .persistenceUnit("secondaryPersistenceUnit")
                .build();

    }

    @Bean(name = "entityManagerSecondary")
    public EntityManager entityManagerSecondary(EntityManagerFactoryBuilder builder) {
        return entityManagerFactorySecondary(builder).getObject().createEntityManager();
    }

    @Bean(name = "transactionManagerSecondary")
    public PlatformTransactionManager transactionManagerSecondary(EntityManagerFactoryBuilder builder) {
        return new JpaTransactionManager(entityManagerFactorySecondary(builder).getObject());
    }
}
```
以上完成之后，多数据源配置就已经完成了，接下来就可以操作数据啦！  

最后， 详细代码可以查看本示例的 Demo。


