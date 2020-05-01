## Spring Boot 配置 Actuator 监控应用

我们知道 Spring Boot 是一个用于快速开发Java Web 的框架，不需要太多的配置即可使用 Spring 的大量功能。Spring Boot 遵循“约定大于配置的理念”，采用包扫描和自动化配置的机制来加载依赖 Jar 中的 Spring Bean，不需要任何 Xml 配置，就可以实现 Spring 的所有配置。虽然这样做的好处在于我们不需要像使用 Spring 那样编写一大堆的 XML 配置代码，能让我们的代码变得非常简洁，但整个应用的实例创建与依赖关系都被分散到了注解上，使得我们分析整个应用中资源和实例的各种关系变得非常的困难。

不过，Spring Boot 提供了 Actuator 来监控应用，Actuator 提供了一系列的 RESTful API 让我们可以查看应用配置信息，如自动化配置信息、Spring Bean 信息。

### Actuator 接口

Actuator 提供了 13 个接口，可以分为三大类：应用配置接口、度量接口和操作控制接口。
- 应用配置接口：可以查看应用在运行期的静态信息，如自动配置信息、加载的 Spring Bean 信息、yml 文件配置信息、环境信息、请求映射信息等。
- 度量接口：主要是运行期的动态信息，如堆栈、请求连、一些健康指标、metrics 信息等。
- 操作控制接口：主要是指 shutdown，用户可以发送一个请求将应用的监控功能关闭。

接口列表如下：
|HTTP 方法 | 路径 | 作用 |
|---|---|---|
| GET | /auditevents | 公开当前应用程序的审核事件信息。|
| GET | /beans | 显示应用程序中所有 Spring Bean 的完整列表 |
| GET | /conditions |提供一份自动配置生效的条件情况，记录哪些自动配置条件通过了，哪些没通过。 |
| GET | /configprops |显示所有配置信息。|
| GET | /env	| 显示所有的环境属性。|
| GET | /env/{name} | 根据名称获取特定的环境属性值。|
| GET | /flyway | 显示已应用的所有 Flyway 数据库迁移。需要一个或多个 Flyway Bean。
| GET | /health | 显示应用程序运行状况信息。up 表示成功，down 表示失败。|
| GET | /httptrace | 显示 HTTP 跟踪信息（默认情况下，最近 100 个HTTP 请求-响应）|
| GET | /info | 获取应用程序的定制信息，这些信息由 info 打头的属性提供。|
| GET | /loggers| 显示和修改应用程序中 Logger 的配置。|
| GET | /metrics | 显示当前应用程序的“度量指标信息”，如内存用量和 HTTP 请求计数。|
| GET | /metrics/{name} | 显示指定名称的应用程序的度量指标信息。|
| GET | /mappings | 显示所有 @RequestMapping 的 url 整理列表。|
| GET | /shutdown | 使应用程序正常关闭。默认禁用。|
| GET | /threaddump | 获取线程活动的快照。|


### 配置 Actuator 服务

**引入依赖**

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-web</artifactId>
</dependency>
<!-- Spring Boot Actuator 对外暴露应用的监控信息，Jolokia 提供使用 HTTP 接口获取 JSON 格式的数据 -->
<dependency>
    <groupId>org.jolokia</groupId>
    <artifactId>jolokia-core</artifactId>
</dependency>
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-actuator</artifactId>
</dependency>
<dependency>
    <groupId>com.googlecode.json-simple</groupId>
    <artifactId>json-simple</artifactId>
    <version>1.1</version>
</dependency>
```

**配置 application.properties 文件**

```properties
server.port=8080

# 项目名称
info.app.name=Spring-Boot-Actuator
info.app.version= 1.0.0
info.app.test=test

####### Actuator 配置 #######
# 打开所有监控端点，默认仅公开 health 和 info 端点
management.endpoints.web.exposure.include=*
# management.endpoint.health.show-details=always
# Actuator 的访问路径，为了安全起见，一般使用独立的地址访问监控信息，同时也避免和自己应用的路径映射地址重复
management.endpoints.web.base-path=/monitor
# 关掉安全认证，但一般为了安全起见，不建议关闭
management.security.enabled=false
# 开启使用接口关闭 Spring Boot 应用
management.endpoint.shutdown.enabled=true
```
### 部分接口使用示例

**beans**

查看 Spring 容器管理的所有 Bean，展示了 Bean 的别名、类型、是否单例、类的地址、依赖等信息。访问 `http://localhost/monitor/beans`，返回部分信息如下：

```json
{
    "contexts": {
        "application": {
            "beans": {
                "org.springframework.boot.actuate.autoconfigure.health.HealthEndpointWebExtensionConfiguration$ReactiveWebHealthConfiguration": {
                    "aliases": [],
                    "scope": "singleton",
                    "type": "org.springframework.boot.actuate.autoconfigure.health.HealthEndpointWebExtensionConfiguration$ReactiveWebHealthConfiguration$$EnhancerBySpringCGLIB$$ae52c267",
                    "resource": null,
                    "dependencies": []
                },
                "endpointCachingOperationInvokerAdvisor": {
                    "aliases": [],
                    "scope": "singleton",
                    "type": "org.springframework.boot.actuate.endpoint.invoker.cache.CachingOperationInvokerAdvisor",
                    "resource": "class path resource [org/springframework/boot/actuate/autoconfigure/endpoint/EndpointAutoConfiguration.class]",
                    "dependencies": [
                        "environment"
                    ]
                }
            }
        }
    }
}
```

**conditions**

使用 conditions 可以在应用运行时查看代码了某个配置在什么条件下生效，或者某个自动配置为什么没有生效。访问 `http://localhost:8080/monitor/conditions`，返回部分信息如下：

```json
{
    "contexts": {
        "application": {
            "positiveMatches": {
                "AdminServerWebConfiguration.ReactiveRestApiConfiguration": [
                    {
                        "condition": "OnWebApplicationCondition",
                        "message": "found ReactiveWebApplicationContext"
                    }
                ],
                "AuditAutoConfiguration#auditListener": [
                    {
                        "condition": "OnBeanCondition",
                        "message": "@ConditionalOnMissingBean (types: org.springframework.boot.actuate.audit.listener.AbstractAuditListener; SearchStrategy: all) did not find any beans"
                    }
                ],
                "AuditAutoConfiguration.AuditEventRepositoryConfiguration": [
                    {
                        "condition": "OnBeanCondition",
                        "message": "@ConditionalOnMissingBean (types: org.springframework.boot.actuate.audit.AuditEventRepository; SearchStrategy: all) did not find any beans"
                    }
                ]
            }
        }
    }
}
```

**health**

health 主要用来检查应用的运行状态，这是我们使用最高频的一个监控点。通常使用此接口提醒我们应用实例的运行状态，以及应用不”健康“的原因，比如数据库连接、磁盘空间不够等。健康状态分为UP（正常）和DOWN（故障）状态。访问 `http://localhost:8080/actuator/health`，返回信息如下：

```json
{
    "status": "UP"
}
```

**info**

info 就是我们自己配置在 application.properties 文件中以 info 开头的配置信息。访问 `http://localhost:8080/actuator/info`，返回信息如下：

```json
{
    "app": {
        "name": "Spring-Boot-Actuator",
        "version": "1.0.0",
        "test": "test"
    }
}
```

**mappings**

显示所有通过注解 ＠RequestMapping 设置的 URL 映射，可以通过此来查看 URL 对应的控制器。访问`http://localhost:8080/monitor/mappings`，显示部分信息如下：

```json
{
    "contexts": {
        "application": {
            "mappings": {
                "dispatcherHandlers": {
                    "webHandler": [
                        {
                            "predicate": "{[/monitor/auditevents],methods=[GET],produces=[application/vnd.spring-boot.actuator.v2+json || application/json]}",
                            "handler": "public org.reactivestreams.Publisher<org.springframework.http.ResponseEntity<java.lang.Object>> org.springframework.boot.actuate.endpoint.web.reactive.AbstractWebFluxEndpointHandlerMapping$ReadOperationHandler.handle(org.springframework.web.server.ServerWebExchange)",
                            "details": {
                                "handlerMethod": {
                                    "className": "org.springframework.boot.actuate.endpoint.web.reactive.AbstractWebFluxEndpointHandlerMapping.ReadOperationHandler",
                                    "name": "handle",
                                    "descriptor": "(Lorg/springframework/web/server/ServerWebExchange;)Lorg/reactivestreams/Publisher;"
                                },
                                "handlerFunction": null,
                                "requestMappingConditions": {
                                    "consumes": [],
                                    "headers": [],
                                    "methods": [
                                        "GET"
                                    ],
                                    "params": [],
                                    "patterns": [
                                        "/monitor/auditevents"
                                    ],
                                    "produces": [
                                        {
                                            "mediaType": "application/vnd.spring-boot.actuator.v2+json",
                                            "negated": false
                                        },
                                        {
                                            "mediaType": "application/json",
                                            "negated": false
                                        }
                                    ]
                                }
                            }
                        }
                }
            }
        }
    }
}
```
**metrics**

metrics 可以显示 Spring Boot 的指标，如己有内存、未占用内存、垃圾回收次数、类信息等。访问 `http://localhost/monitor/metrics`，返回部分信息如下：

```json
{
    "names": [
        "jvm.buffer.memory.used",
        "jvm.memory.used",
        "jvm.gc.memory.allocated",
        "jvm.memory.committed",
        "http.server.requests",
        "jvm.gc.max.data.size",
        "logback.events",
        "system.cpu.count",
        "jvm.memory.max",
        "jvm.buffer.total.capacity",
        "jvm.buffer.count",
        "process.files.max",
        "jvm.threads.daemon",
        "process.start.time",
        "jvm.gc.live.data.size",
        "process.files.open",
        "process.cpu.usage",
        "jvm.gc.pause",
        "process.uptime",
        "system.load.average.1m",
        "system.cpu.usage",
        "jvm.threads.live",
        "jvm.classes.loaded",
        "jvm.classes.unloaded",
        "jvm.threads.peak",
        "jvm.gc.memory.promoted"
    ]
}
```

根据指标名称进行访问，可以获取每个指标的信息。访问 `http://localhost/monitor/metrics/jvm.memory.used`，返回信息如下：

```json
{
    "name": "jvm.memory.used",
    "measurements": [
        {
            "statistic": "VALUE",
            "value": 1.6202468E8
        }
    ],
    "availableTags": [
        {
            "tag": "area",
            "values": [
                "heap",
                "nonheap"
            ]
        },
        {
            "tag": "id",
            "values": [
                "Compressed Class Space",
                "PS Old Gen",
                "PS Survivor Space",
                "Metaspace",
                "PS Eden Space",
                "Code Cache"
            ]
        }
    ]
}
```

### 定制 Actuator 监控

**启用或禁用端点**

默认情况下，除 shutdown 之外的所有端点均处于启用状态。要配置端点的启用，可以使用其 `management.endpoint.<id>.enabled ` 属性。以下示例启用 shutdown 端点：

```properties
management.endpoint.shutdown.enabled=true
```
如果你只想打开一两个接口，那就先禁用全部接口，然后启用那几个你要的，这样更方便。以下示例启用 info 端点并禁用所有其他端点：

```properties
management.endpoints.enabled-by-default=false
management.endpoint.info.enabled=true
```

**公开端点**

由于端点可能包含敏感信息，Actuator 默认了公开公开 health 和 info 端点。我们可以使用 `management.endpoints.web.exposure.<include/eclude>` 属性配置哪些端点公开，哪些端点不公开。`include` 属性列出了公开的端点的 ID。`exclude` 属性列出了不应公开的端点的 ID。

例如，要通过 HTTP 公开除 env 和 beans 端点之外的所有内容：
```properties
management.endpoints.web.exposure.include=*
management.endpoints.web.exposure.exclude=env,beans
```

### 参考

Spring Boot Actuator: https://docs.spring.io/spring-boot/docs/current/reference/html/production-ready-features.html#production-ready


