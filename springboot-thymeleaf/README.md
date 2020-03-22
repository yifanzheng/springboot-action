## Spring Boot 集成 Thymeleaf 模板引擎
Thymeleaf 是 Web 和独立环境的现代服务器端 Java 模板引擎，能够处理 HTML，XML，JavaScript，CSS 甚至纯文本。
Thymeleaf 的主要目标是提供一种优雅和高度可维护的创建模板的方式。为了实现这一点，它建立在自然模板的概念上，将其逻辑注入到模板文件中，不会影响模板被用作设计原型。

### 特性

 - Thymeleaf 在有网络和无网络的环境下皆可运行，即它可以让美工在浏览器查看页面的静态效果，也可以让程序员在服务器查看带数据的动态页面效果。这是由于它支持 HTML 原型，然后在 HTML 标签里增加额外的属性来达到模板+数据的展示方式。浏览器解释 HTML 时会忽略未定义的标签属性，所以 Thymeleaf 的模板可以静态地运行；当有数据返回到页面时，Thymeleaf 标签会动态地替换掉静态内容，使页面动态显示。

- Thymeleaf 开箱即用的特性。它提供标准和Spring 标准两种方言，可以直接套用模板实现JSTL、 OGNL 表达式效果，避免每天套模板、该JSTL、改标签的困扰。同时开发人员也可以扩展和创建自定义的方言。

- Thymeleaf 提供 Spring 标准方言和一个与 SpringMVC 完美集成的可选模块，可以快速的实现表单绑定、属性编辑器、国际化等功能。

### 添加 Thymeleaf 依赖

```xml
<dependency>
      <groupId>org.springframework.boot</groupId>
      <artifactId>spring-boot-starter-thymeleaf</artifactI>
</dependency>
```

### Thymeleaf 配置

```properties
# 设定文件路径。末尾必须加上“/”，不然加载不到文件
spring.thymeleaf.prefix=calsspath:/templates/ 
# 加载的文件后缀名
spring.thymeleaf.suffix=.html
spring.thymeleaf.mode=HTML5
spring.thymeleaf.encoding=UTF-8
spring.thymeleaf.content-type=text/html
# 关闭缓存，及时刷新，上线生产环境改为true
spring.thymeleaf.cache=false
```

### Thymeleaf 常用标签使用方式

**表达式**

`${}`：变量表达式。  
`*{}`：选择所有值表达式，比如 *{name}，先从可用值查找，如果有上下文，上层是 Object，则查找Object 中的 name 属性值。一般跟在 `th:object` 后，直接取 `object` 中的属性。
`#{}`：消息表达式，国际化时使用，也可使用内置对象，如 dates 格式化数据。  
`@{}`：链接表达式，配合 link、src、href 使用，支持决定路径和相对路径。  
`~{}`：片段表达式，用来引入公共部分的代码片段，并进行传值操作。

**对象引用方式** 

> th:object="${对象}"

**th:text 与 th:utext 区别**

`text` 是非转义文本，会原样输出引用的内容；`utext` 是转义文本，会解析内容，然后输出；
例：
```html
<p th:text="'Any characters, <br>Let\'s</br> go!'"></p>
```
页面显示效果：Any characters, &lt;br>Let's &lt;/br> go!

```html
<p th:utext="'Any characters, <br>Let's</br> go!'"></p>
```
页面显示效果：Anycharacters,  
Let's  
go!

**URL**

Thymeleaf 对于 URL 的处理是通过语法 @{...} 来处理的。 另外，如果需要 Thymeleaf 对 URL 进行渲染，那么务必使用 th:href，th:src 等属性。

- 引用链接

```html
<a th:href="@{http://www.baidu.com}"></a>

<a href="details.html" th:href="@{/order/{orderId}/details(orderId=${o.id})}">view</a>
```
 URL 最后的 `(orderId=${o.id})` 表示将括号内的内容作为 URL 参数处理，该语法避免使用字符串拼接，大大提高了可读性

- 引用静态资源文件 js/css

```java
<script th:src="@{/static/js/test.js}"></script>
```
**条件判断 th:if/th:unless**
```java
<div th:if="${value}==18">等于18</div>
<div th:if="${value} gt 18">大于18</div>
<div th:if="${value} lt 18">小于18</div>
<div th:if="${value} ge 18">大于等于18</div>
<div th:if="${value} le 18">小于等于18</div>
```
标签只有在 `th:if` 中条件成立时才显示;`th:unless` 与 `th:if` 恰好相反，只有表达式中的条件不成立，才会显示其内容。  

比较: `>, <, >=, <= (gt, lt, ge, le)`  
等值运算符: `==, != (eq, ne)`  

**循环 th:each**
```java
<tr th:each="person:${list}">
  <td th:text="${person.name}"></td>
  <td th:text="${person.age}"></td>
</tr>
```
**th:switch 和 th:case 多路选择**
```java
<div th:switch="${user.name}">
  <p th:case="'lee'">lee</p>
  <p th:case="#{roles.manager}"></p>
  <p th:case="#{roles.super}"></p>
</div>
```
**内嵌变量**   

为了模板更加易用，Thymeleaf 还提供了一系列 Utility 对象（内于Context中），可以通过 `#` 直接访问。

dates：java.util.Date 的功能方法类。    
calendars：类似 #dates，面向 java.util.Calendar。  
numbers：格式化数字的功能方法类。  
strings：字符串对象的功能类。  
objects：对 objects 的功能类操作。  
bools： 对布尔值求值的功能方法。  
arrays：对数组的功能类方法。  
lists：对 lists 功能类方法。  
sets：对 sets 功能类的方法。   
maps：对 maps 功能类的方法。   

- 时间类型转换

```java
/*
 * 用指定格式日期模式格式化
 * 适用于 Arrays、Lists 或 Sets
 */
${#dates.format(date,'yyyy-MM-dd')}

${#dates.arrayFormat(dateArray,'yyyy-MM-dd')}

${#dates.listFormat(dateList,'yyyy-MM-dd')}

${#dates.setFormat(dateSet,'yyyy-MM-dd')}

/*
 * 为当前日期创建一个日期（java.util.Date）对象
 */
${#dates.createNow()}

/*
 * 为当前日期创建一个日期（java.util.Date）对象（时间设置为 00:00）
 */
${#dates.createToday()}
```

### 参考

[Thymeleaf 官方文档](https://www.thymeleaf.org/doc/tutorials/2.1/thymeleafspring.html#integrating-thymeleaf-with-spring)

