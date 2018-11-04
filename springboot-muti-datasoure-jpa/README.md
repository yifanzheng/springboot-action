# springboot 使用Jpa,JdbcTemplate整合多数据源
### applicattion.properties配置数据源信息
```java
# 数据源一
datasource.primary.jdbc-url=jdbc:mysql://localhost:3306/db_novel?autoReconnect=true&useUnicode=true
datasource.primary.diver-class-name=com.mysql.jdbc.Driver
datasource.primary.username=root
datasource.primary.password=root

# 数据源二
datasource.secondary.jdbc-url=jdbc:mysql://localhost:3306/game?autoReconnect=true&useUnicode=true
datasource.secondary.diver-class-name=com.mysql.jdbc.Driver
datasource.secondary.username=root
datasource.secondary.password=root

# jpa配置
spring.jpa.database=mysql
spring.jpa.generate-ddl=true
spring.jpa.show-sql=true
spring.jpa.open-in-view=false
spring.jpa.hibernate.ddl-auto=update
spring.jpa.hibernate.naming.physical-strategy=org.springframework.boot.orm.jpa.hibernate.SpringPhysicalNamingStrategy
spring.jpa.hibernate.naming.implicit-strategy=org.springframework.boot.orm.jpa.hibernate.SpringImplicitNamingStrategy
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQL5Dialect
```
### 配置jpa数据源
```java
@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        entityManagerFactoryRef = "entityManagerFactoryPrimary",
        transactionManagerRef = "transactionManagerPrimary",
        basePackages = {"com.multi.jpa.primarydatasource"})
public class PrimaryDataSourceConfiguration {

    @Autowired
    private JpaProperties jpaProperties;

    @Bean(name = "primaryDatasource")
    @Primary
    @ConfigurationProperties(prefix = "datasource.primary")
    public DataSource dataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "entityManagerPrimary")
    @Primary
    public EntityManager entityManager(EntityManagerFactoryBuilder builder){
        return entityManagerFactoryPrimary(builder).getObject().createEntityManager();
    }

    @Bean(name = "entityManagerFactoryPrimary")
    @Primary
    public LocalContainerEntityManagerFactoryBean entityManagerFactoryPrimary(EntityManagerFactoryBuilder builder) {
        return builder.dataSource(dataSource())
                .properties(getVendorProperties())
                // 设置映射的实体类
                .packages("com.multi.jpa.primarydatasource.domain")
                .persistenceUnit("primaryPersistenceUnit")
                .build();
    }

    private Map<String, Object> getVendorProperties() {
        return jpaProperties.getHibernateProperties(new HibernateSettings());
    }

    @Bean(name = "transactionManagerPrimary")
    @Primary
    public PlatformTransactionManager transactionManagerSecondary(EntityManagerFactoryBuilder builder) {
        return new JpaTransactionManager(entityManagerFactoryPrimary(builder).getObject());
    }
}
```
### 配置JdbcTemplate数据源
```java
@Configuration
public class SecondaryDataSourceConfiguration {

    @Bean(name = "secondaryDatasource")
    @ConfigurationProperties(prefix = "datasource.secondary")
    public DataSource dataSource(){
        return new HikariDataSource();
    }

    @Bean(name = "secondaryJdbcTemplate")
    public JdbcTemplate jdbcTemplate(@Qualifier("secondaryDatasource") DataSource dataSource){
        return new JdbcTemplate(dataSource);
    }
}
```
