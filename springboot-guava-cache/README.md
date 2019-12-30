## springboot 集成 Guava Cache 实现缓存机制

Guava Cache 是一个全内存的本地缓存实现，而且提供了线程安全机制，相比于数据库或 Redis 存储，访问内存中的数据会更加高效。  

Guava 官网介绍，下面的这几种情况可以考虑使用 Guava Cache：

- 愿意消耗一些内存空间来提升速度。

- 预料到某些键会被多次查询。

- 缓存中存放的数据总量不会超出内存容量。

所以，可以将频繁用到的少量数据存储到 Guava Cache 中，以提高程序性能。下面我们来看看 Guava Cache 具体用法。

### 添加依赖

在 pom.xml 中添加 `spring-boot-starter-cache` 和 `guava` 依赖。

```java
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-cache</artifactId>
</dependency>

<!--guava 依赖-->
<dependency>
    <groupId>com.google.guava</groupId>
    <artifactId>guava</artifactId>
    <version>27.0.1-jre</version>
</dependency>
```
### 准备数据

模拟数据库的数据

```java
/**
 * 数据工厂，模拟数据库的数据
 *
 * @author star
 **/
public class DataFactory {

    private DataFactory() {
    }

    private static List<UserDto> userDtoList;

    static {
        // 初始化集合
        userDtoList = new ArrayList<>();

        UserDto user = null;
        for (int i = 0; i < 5; i++) {
            user = new UserDto();
            user.setName("star" + i);
            user.setAge(23);
            userDtoList.add(user);
        }
    }

    public static List<UserDto> getUserDaoList() {
        return userDtoList;
    }
}
```

### 创建 Guava Cache 配置类

Guava Cache 配置比较简洁，下面配置了缓存数据的过期时间是 10s，最大缓存容量是 1000 个。

```java
@Configuration
@EnableCaching
public class GuavaCacheConfig {

    /**
     * 设置缓存管理器
     */
    @Bean
    public CacheManager cacheManager(){
        GuavaCacheManager cacheManager = new GuavaCacheManager();

        cacheManager.setCacheBuilder(CacheBuilder.newBuilder()
                // 缓存过期时间
                .expireAfterWrite(10, TimeUnit.SECONDS)
                // 缓存最大容量是 1000
                .maximumSize(1000)
        );
        return cacheManager;
    }

}
```
Guava Cache 除了代码中提到的设置缓存过期时间的策略外，还有其他的策略。下面是 Guava Cache 设置缓存过期时间的策略：

- expireAfterAccess: 当缓存项在指定的时间段内没有被读或写就会被回收。
- expireAfterWrite：当缓存项在指定的时间段内没有更新就会被回收,如果我们认为缓存数据在一段时间后数据不再可用，那么可以使用该种策略。

- refreshAfterWrite：当缓存项上一次更新操作之后的多久会被刷新。

### 编写业务代码

- 编写 DAO 层

```java
/**
 * UserRepository
 *
 * @author star
 **/
@Repository
public class UserRepository {

    /**
     * 获取用户信息(此处是模拟的数据)
     */
    public UserDto getUserByName(String username) {
        UserDto user = getUserFromList(username);
        return user;
    }

    /**
     * 从模拟的数据集合中筛选 username 的数据
     */
    private UserDto getUserFromList(String username) {

        List<UserDto> userDaoList = DataFactory.getUserDaoList();
        for (UserDto user : userDaoList) {
            if (Objects.equals(user.getName(), username)) {
                return user;
            }
        }
        return null;
    }
}
```

- 编写 Service 层

```java
/**
 * UserService
 *
 * @author star
 **/
@Service
@CacheConfig(cacheNames = "guavaCache") // 声明缓存的名称
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Cacheable(key = "#name")
    public UserDto getUserByName(String name) {
        System.out.println("从数据库中获取数据，而不是读取缓存");
        return userRepository.getUserByName(name);
    }

}
```

由于在上一篇 [springboot-cache](../springboot-cache) 已经对缓存用法做了详细说明，这里就简单介绍一下：

- `@Cacheable`: 主要针对方法配置，能够根据方法的请求参数对其结果进行缓存。同时在查询时，会先从缓存中获取，若不存在才再发起对数据库的访问。

- `@CachePut`：配置于方法上时，能够根据参数定义条件来进行缓存，其与 @Cacheable 不同的是，它不会去检查缓存中是否存在之前执行过的结果，而是每次都会执行该方法，并将执行结果以键值对的形式存入指定的缓存中，所以主要用于数据新增和修改操作上。

- `@CacheEvict`：配置于方法上时，表示从缓存中移除相应数据。

- 编写 Controller 层

```java
/**
 * UserResource
 *
 * @author star
 **/
@RestController
@RequestMapping("/api")
public class UserResource {

    @Autowired
    private UserService userService;

    @Autowired
    private CacheManager cacheManager;

    @GetMapping("/users/{name}")
    public ResponseEntity<UserDto> getUser(@PathVariable String name) {
        System.out.println("==================");
        UserDto user = userService.getUserByName(name);
        System.out.println(cacheManager.toString());
        return ResponseEntity.ok(user);
    }
}
```
### 演示

通过多次向接口 `http://localhost:8080/api/users/star1` GET 数据来观察效果：

![get](./asset/images/get.png)

可以看到缓存的启用和效果如下所示：  

![result](./asset/images/result.png)
