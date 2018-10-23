# springboot实现定时任务task
### 使用@EnableScheduling开启定时任务，会自动扫描
```java
@SpringBootApplication
// 开启定时任务
@EnableScheduling
public class TaskApplication {

	public static void main(String[] args) {
		SpringApplication.run(TaskApplication.class, args);
	}
}
```
### 定义@Component作为组件被容器扫描
```java
**
 * 定时任务
 *
 * @author kevin
 * @date 2018-10-22 21:51
 **/
@Component
public class TaskConfig {

    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("HH:mm:ss");

    /**
     * 每隔3秒执行一次
     */
    //@Scheduled(fixedRate = 3000)
    @Scheduled(cron = "4-10 * * * * ?") // 周期在4-10秒，每隔一秒执行。-
    public void reportCurrentTime() {

        System.out.println("目前的时间："+DATE_FORMAT.format(new Date()));
    }
}
```
### cron表达式
表达式生成地址：<http://cron.qqe2.com>