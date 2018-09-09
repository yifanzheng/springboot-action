# SpringBoo整合Redis
- 修改pom.xml文件，添加Redis项目依赖
```java
<!--redis-->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-redis</artifactId>
</dependency>
```
- 在资源文件`application.properties`中对Redis进行配置
```java
# Redis数据库索引（默认为0）
spring.redis.database=1
# Redis服务器地址（默认本地，可以根据需要修改）
spring.redis.host=127.0.0.1
# Redis服务器连接端口
spring.redis.port=6379
# Redis服务器连接密码（默认为空）
spring.redis.password=
# 连接池最大连接数（使用负值表示没有限制）
spring.redis.pool.max-active=1000
# 连接池最大阻塞等待时间（使用负值表示没有限制）
spring.redis.pool.max-wait=-1
# 连接池中的最大空闲连接
spring.redis.pool.max-idle=10
# 连接池中的最小空闲连接
spring.redis.pool.min-idle=2
# 连接超时时间（毫秒）
spring.redis.timeout=0
```
- 设置数据存入Redis缓存的序列化方式-Json格式
在pom.xml文件中添加Json依赖
```java
<dependency>
    <groupId>com.fasterxml.jackson.core</groupId>
    <artifactId>jackson-databind</artifactId>
    <version>2.9.5</version>
</dependency>
```


```java
@Configuration
@EnableCaching
public class RedisConfig {

     /**
     * redisTemplate 序列化使用的jdkSerializeable, 存储二进制字节码。
      如果不配置Serializer，那么存储的时候缺省使用String，如果用User类型存储，那么会提示错误User can't cast to String！
     * @param redisConnectionFactory
     * @return
     */
    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory);

        // 使用Jackson2JsonRedisSerialize 替换默认序列化
        Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        objectMapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);

        jackson2JsonRedisSerializer.setObjectMapper(objectMapper);

        // 设置value的序列化规则和 key的序列化规则
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(jackson2JsonRedisSerializer);
        //开启事务
        redisTemplate.setEnableTransactionSupport(true);
        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }
}
```
- RedisTemplate和StringRedisTemplate区别
1. StringRedisTemplate是RedisTemplate的一个直接子类。
2. RedisTemplate是一个泛型类，StringRedisTemplate则不是。
3. StringRedisTemplate默认采用是String的序列化策略；
   RedisTemplate默认采用是JDK的序列化策略。
4. 当redis存入的数据是字符串数据时，使用StringRedisTemplate即可；
   当redis中的数据是复杂的对象类型，存取时不做任何转换，使用RedisTemplate最合适。

- RedisTemplate的相关方法

```java
   redisTemplate.opsForValue();//操作字符串
   redisTemplate.opsForHash();//操作hash
   redisTemplate.opsForList();//操作list
   redisTemplate.opsForSet();//操作set（无序）
   redisTemplate.opsForZSet();//操作有序的set
```
