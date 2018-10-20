# springboot 整合 Guava 实现缓存机制
由于springboot 2.x不支持Guava缓存机制，故使用springboot 1.5.x。

### pom.xml文件加入Guava相关依赖
```java
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-cache</artifactId>
</dependency>

<!--guava缓存依赖-->
<dependency>
    <groupId>com.google.guava</groupId>
    <artifactId>guava</artifactId>
    <version>20.0</version>
</dependency>
```
### 配置GuavaCacheManager
```java
@Configuration
@EnableCaching
public class GuavaCacheConfig {

    /**
     * expireAfterAccess: 当缓存项在指定的时间段内没有被读或写就会被回收。
     * expireAfterWrite：当缓存项在指定的时间段内没有更新就会被回收,如果我们认为缓存数据在一段时间后数据不再可用，那么可以使用该种策略。
     * refreshAfterWrite：当缓存项上一次更新操作之后的多久会被刷新。
     * @return
     */
    @Bean
    public CacheManager cacheManager(){
        GuavaCacheManager cacheManager = new GuavaCacheManager();

        cacheManager.setCacheBuilder(CacheBuilder.newBuilder()
                // 缓存有效时间
                .expireAfterWrite(10, TimeUnit.SECONDS)
                .maximumSize(1000)
        );
        return cacheManager;
    }

}
```
