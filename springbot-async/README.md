# springboot 使用@Async实现异步调用
启动时在类加上`@EnableAsync`开启异步功能,需要执行异步方法上加入`@Async`。在方法上加上`@Async`之后，底层使用多线程技术。

### 案列
```java
@Service
@EnableAsync // 开启异步功能
public class AsyncService {

    @Async // 相当于这个方法重新开辟了新的线程去执行。
    public String getContent(){
        System.out.println("2");
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("3");

        return "success!";
    }
}
```


