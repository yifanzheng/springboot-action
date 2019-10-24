## Spring Boot 集成 JPA 操作数据库
Jpa (Java Persistence API) 是 Sun 官方提出的 Java 持久化规范。它为 Java 开发人员提供了一种对象/关联映射工具来管理 Java 应用中的关系数据。它的出现主要是为了简化现有的持久化开发工作和整合 ORM 技术，结束现在 Hibernate，TopLink，JDO 等 ORM 框架各自为营的局面。

下面我们来看看，Spring Boot 怎样集成 JPA。 

### 配置数据源信息

- application.properties

```properties
spring.datasource.driver-class-name=com.mysql.jdbc.Driver
spring.datasource.url=jdbc:mysql://localhost:3306/game?useSSL=false&serverTimezone=CTT&useUnicode=true&characterEncoding=utf-8
spring.datasource.username=root
spring.datasource.password=root

spring.jpa.show-sql=true
spring.jpa.hibernate.ddl-auto=update
spring.jpa.open-in-view=false
```

### 配置 JPA

```java
@Configuration
@EnableTransactionManagement
@EnableJpaAuditing
@EnableJpaRepositories(basePackages = "com.example.jpa.repository")
@EntityScan(basePackages = "com.example.jpa.entity")
public class DataSourceConfig {

}
```

### 创建映射实体类

```java
/** 审计类 */
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class AbstractAuditor implements Serializable {

   private static final long serialVersionUID = -8636810082784692918L;

   @CreatedDate
   @Temporal(TIMESTAMP)
   private Date createdDate;

   @LastModifiedDate
   @Temporal(TIMESTAMP)
   private Date lastModifiedDate;

   public Date getCreatedDate() {
      return createdDate;
   }

   public void setCreatedDate(Date createdDate) {
      this.createdDate = createdDate;
   }

   public Date getLastModifiedDate() {
      return lastModifiedDate;
   }

   public void setLastModifiedDate(Date lastModifiedDate) {
      this.lastModifiedDate = lastModifiedDate;
   }
}


@Entity
public class People extends AbstractAuditor {

    private static final long serialVersionUID = -2189163594057781698L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", columnDefinition = "nvarchar(20)")
    private String name;

    @Column(name = "age", columnDefinition = "int")
    private Integer age;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }
}
```

### 编写一个继承 JpaRepository 的接口完成数据访问

```java
public interface PeopleRepository extends JpaRepository<People, Long> {

}
```

### 参考

Spring Data JPA 官方文档：[https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#reference](https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#reference)