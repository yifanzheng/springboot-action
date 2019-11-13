## Spring Boot 集成 Mybatis（XML 方式）和 PageHelper 分页插件

MyBatis 是一款优秀的持久层框架，它对 JDBC 的操作数据库的过程进行封装，支持定制化 SQL、存储过程以及高级映射，避免了几乎所有的 JDBC 代码和手动设置参数以及获取结果集。MyBatis 可以使用简单的 XML 或注解来配置和映射原生信息，将接口和 Java 的 POJO（Plain Old Java Objects，普通的 Java 对象）映射成数据库中的记录。

通俗地讲，MyBatis 就是我们使用 Java 程序操作数据库时的一种工具，可以简化我们使用 JDBC 时的很多操作，而且还简化了数据库记录与 POJO 之间的映射方式。

### 添加相关依赖

**MyBatis 依赖**

```xml
<dependency>
    <groupId>org.mybatis.spring.boot</groupId>
    <artifactId>mybatis-spring-boot-starter</artifactId>
    <version>1.3.1</version>
</dependency>
```

**PageHelper 分页插件依赖**

```xml
<dependency>
    <groupId>com.github.pagehelper</groupId>
    <artifactId>pagehelper-spring-boot-starter</artifactId>
    <version>1.2.3</version>
</dependency>
```

### 配置数据源信息

- application.properties

```properties
# 数据源配置
spring.datasource.url=jdbc:mysql://localhost:3306/game?serverTimezone=UTC&useUnicode=true&characterEncoding=utf-8
spring.datasource.username=root
spring.datasource.password=root
spring.datasource.driver-class-name=com.mysql.jdbc.Driver

# 配置数据源，使用阿里巴巴的 druid 数据库连接池
spring.datasource.druid.initial-size=1
spring.datasource.druid.min-idle=1
spring.datasource.druid.max-active=20
spring.datasource.druid.test-on-borrow=true
spring.datasource.druid.stat-view-servlet.allow=true
```

### 配置 MyBatis 与 PageHelper 信息

```properties
# MyBatis 配置
mybatis.config-location=classpath:mybatis/mybatis-config.xml
mybatis.mapper-locations=classpath:mybatis/mapper/*.xml
mybatis.type-aliases-package=com.example.mybatis.entity

# PagerHelper  分页
pagehelper.helperDialect=mysql
pagehelper.reasonable=true
pagehelper.supportMethodsArguments=true
pagehelper.params=count=countSql
```

### Mapper 映射配置

Mapper 类是一个接口，它的实现类不是一个 JAVA 类，而是一个与之对应的 XML 文件。Mapper 类中声明的方法对应 XML 文件中的一段 SQL 语句 。 

**Mapper 类**

```java
public interface StudentMapper {

    Student selectById(Integer id);

    List<Student> selectAll();

    void updateStudent(Student student);

    void insertStudent(Student student);

    void deleteStudent(Integer id);

}
```

**Mapper 映射的 XML 文件**

```xml
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="com.example.mybatis.mapper.StudentMapper">
    <resultMap id="BaseResultMap" type="com.example.mybatis.entity.Student">
        <result column="id" property="id" javaType="java.lang.Integer"/>
        <result column="name" property="name" javaType="java.lang.String"/>
        <result column="age" property="age" javaType="java.lang.Integer"/>
        <result column="gender"  property="gender" javaType="com.example.mybatis.constant.GenderEnum"/>
    </resultMap>

    <sql id="BaseColumnList" >
        id, name, age, gender
    </sql>

    <select id="selectById" resultMap="BaseResultMap" parameterType="java.lang.Integer">
        SELECT
        <include refid="BaseColumnList"/>
        FROM student
        WHERE id = #{id}
    </select>

    <select id="selectAll" resultMap="BaseResultMap">
        SELECT
        <include refid="BaseColumnList"/>
        FROM student
    </select>

    <insert id="insertStudent">
       INSERT INTO student (
           name,
           age,
           gender
        ) VALUES (
          #{name},
          #{age},
          #{gender}
        )
    </insert>

    <update id="updateStudent">
        UPDATE student
        SET 
         <if test="name != null || name != ''">name = #{name},</if>
         <if test="age != null">age = #{age},</if>
         <if test="gender != null">gender = #{gender}</if>
        WHERE id = #{id}
    </update>

    <delete id="deleteStudent">
        DELETE FROM student WHERE id = #{id}
    </delete>

</mapper>
```

其中 namespace 指定了该 XML 文件对应的 Mapper 类。resultMap 的标签，定义的是我们 SQL 查询的字段与实体类之间的映射关系。

### 编写 Service 类

```java
@Service
public class StudentService {

    @Autowired
    private StudentMapper studentMapper;

    public Student getStudent(Integer id) {
        Student student = studentMapper.selectById(id);

        return student;
    }

    public List<Student> listStudent(Integer pageNum, Integer pageSize) {
        // 如果 pageSize 为空，默认返回 10 条数据
        if (pageSize == null) {
            pageSize = 10;
        }
        // 分页
        Page<Student> studentPage = PageHelper.startPage(pageNum, pageSize)
                .doSelectPage(() -> studentMapper.selectAll());
        List<Student> students = studentPage.getResult();

        return students;
    }

    public void saveStudent(Student student) {
        studentMapper.insertStudent(student);
    }

    public void updateStudent(Student student) {
        studentMapper.updateStudent(student);
    }

    public void deleteStudent(Integer id) {
        studentMapper.deleteStudent(id);
    }
}
```

最后， 详细代码可以查看本示例的 Demo。

### 参考

MyBatis 中文官网：[http://www.mybatis.cn/](http://www.mybatis.cn/)

PageHelper 官网：[https://pagehelper.github.io/](https://pagehelper.github.io/)


