# springboot 整合Mybatis，反向生成dao层，通用Mapper，分页插件
### 项目依赖
```java
<!--mybatis依赖-->
<dependency>
    <groupId>org.mybatis.spring.boot</groupId>
    <artifactId>mybatis-spring-boot-starter</artifactId>
    <version>1.3.1</version>
</dependency>

<!--mapper依赖-->
<dependency>
    <groupId>tk.mybatis</groupId>
    <artifactId>mapper-spring-boot-starter</artifactId>
    <version>2.0.0</version>
</dependency>

<!--pagehelper分页插件-->
<dependency>
    <groupId>com.github.pagehelper</groupId>
    <artifactId>pagehelper-spring-boot-starter</artifactId>
    <version>1.2.3</version>
</dependency>
```
### 集成 MyBatis Generator
Mybatis Geneator 详解:
><http://blog.csdn.net/isea533/article/details/42102297>
##### 1. 在项目的根目录下添加 generatorConfig.xml 文件，并引入逆向工程核心依赖
   - generatorConfig.xml文件信息
```java
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE generatorConfiguration
        PUBLIC "-//mybatis.org//DTD MyBatis Generator Configuration 1.0//EN"
        "http://mybatis.org/dtd/mybatis-generator-config_1_0.dtd">

<generatorConfiguration>
    <context id="MysqlContext" targetRuntime="MyBatis3Simple" defaultModelType="flat">
        <property name="beginningDelimiter" value="`"/>
        <property name="endingDelimiter" value="`"/>

        <plugin type="tk.mybatis.mapper.generator.MapperPlugin">
            <property name="mappers" value="com.example.druid.utils.MyMapper"/>
        </plugin>
        
        <!-- 数据库连接地址-->
        <jdbcConnection driverClass="com.mysql.jdbc.Driver"
                        connectionURL="jdbc:mysql://localhost:3306/game"
                        userId="root"
                        password="root">
        </jdbcConnection>

        <!-- 对于生成的pojo所在包 -->
        <javaModelGenerator targetPackage="com.example.druid.domain" targetProject="src/main/java"/>

		<!-- 对于生成的mapper所在目录 -->
        <sqlMapGenerator targetPackage="mapper" targetProject="src/main/resources"/>

		<!-- 配置mapper对应的java映射 -->
        <javaClientGenerator targetPackage="com.example.druid.mapper" targetProject="src/main/java"
                             type="XMLMAPPER"/>

        <!--数据库中的表名-->
		<table tableName="student"></table>
		 
    </context>
</generatorConfiguration>
```
 - 在pom.xml中添加逆向工程核心依赖
```java
<!--逆向工程核心依赖-->
<dependency>
    <groupId>org.mybatis.generator</groupId>
    <artifactId>mybatis-generator-core</artifactId>
    <version>1.3.2</version>
    <scope>compile</scope>
    <optional>true</optional>
</dependency>
```
### 根据配置的 generatorConfig.xml 生成通用Mapper
- 启动以下程序，生成相关Mapper
```java
public class GeneratorDisplay {

    public void generator() throws Exception {
        List<String> warnings = new ArrayList<>();

        // 创建逆向工程配置文件
        File configFile = new File("generatorConfig.xml");
        // 配置解析器
        ConfigurationParser parser = new ConfigurationParser(warnings);
        // 解析配置文件
        Configuration configuration = parser.parseConfiguration(configFile);
        DefaultShellCallback shellCallback = new DefaultShellCallback(true);
        MyBatisGenerator generator = new MyBatisGenerator(configuration, shellCallback, warnings);
        generator.generate(null);


    }

    public static void main(String[] args) {
        try {
            new GeneratorDisplay().generator();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
```
### application.properties 配置
```java
# 数据库配置
spring.datasource.url=jdbc:mysql://localhost:3306/game
spring.datasource.username=root
spring.datasource.password=root
spring.datasource.driver-class-name=com.mysql.jdbc.Driver

# 配置数据源，使用阿里巴巴的druid数据库连接池
spring.datasource.druid.initial-size=1
spring.datasource.druid.min-idle=1
spring.datasource.druid.max-active=20
spring.datasource.druid.test-on-borrow=true
spring.datasource.druid.stat-view-servlet.allow=true

# 通用 Mapper 配置
mapper.mappers=com.example.druid.utils.MyMapper
mapper.not-empty=false
mapper.identity=MYSQL

# pagehelper分页
pagehelper.helperDialect=mysql
pagehelper.reasonable=true
pagehelper.supportMethodsArguments=true
pagehelper.params=count=countSql

# mybatis 配置
mybatis.type-aliases-package=com.example.druid.domain
mybatis.mapper-locations=classpath:mapper/*.xml  
```
### 使用@MapperScan注解指定扫描的Mapper
**注意**：这里要导入 `tk.mybatis.spring.annotation.*` 下的`MapperScan`，不要导入`org.mybatis.spring.annotation.*` 下的，否则会报错。
```java
@SpringBootApplication
@MapperScan(basePackages = "com.example.druid.mapper")
public class DruidApplication {

	public static void main(String[] args) {
		SpringApplication.run(DruidApplication.class, args);
	}
}
```

### 自定义Mapper
- 自定义配置mapper.xml文件，手写sql语句
```java
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="com.example.druid.mapper.StudentMapperCustom">
    <resultMap id="BaseResultMap" type="com.example.druid.domain.Student">
        <!--WARNING - @mbg.generated-->
        <result column="id" jdbcType="INTEGER" property="id"/>
        <result column="name" jdbcType="VARCHAR" property="name"/>
        <result column="chinese" jdbcType="REAL" property="chinese"/>
        <result column="english" jdbcType="REAL" property="english"/>
        <result column="math" jdbcType="REAL" property="math"/>
        <result column="gender" jdbcType="CHAR" property="gender"/>
    </resultMap>
    <!--根据id查询-->
    <select id="queryById" resultMap="BaseResultMap" parameterType="java.lang.Integer">
     SELECT name,math,gender FROM student WHERE id = #{id,jdbcType=INTEGER}
    </select>

    <!-- 查询所有记录-->
    <select id="selectAll" resultMap="BaseResultMap">
        SELECT * FROM  student
    </select>
</mapper>
```
- 创建mapper类，要与mapper配置文件中的namespace的名字对应
```java

/**
 * 自定义mapper
 * 注意：方法名（queryById）要与StudentMappeCustom.xml文件中的id对应
 * @author kevin
 **/
public interface StudentMapperCustom {

    /**
     * 根据id查询记录
     * @param id
     * @return
     */
    Student queryById(Integer id);

    /**
     * 查询所有记录
     * @return
     */
    List<Student> selectAll();
}
```



