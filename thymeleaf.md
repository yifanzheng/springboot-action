# SpringBoot整合thymeleaf模板引擎
### 什么是Thymeleaf?
Thymeleaf是Web和独立环境的现代服务器端Java模板引擎，能够处理HTML，XML，JavaScript，CSS甚至纯文本。
Thymeleaf的主要目标是提供一种优雅和高度可维护的创建模板的方式。为了实现这一点，它建立在自然模板的概念上，将其逻辑注入到模板文件中，不会影响模板被用作设计原型。
### 特性
 - 1.Thymeleaf 在有网络和无网络的环境下皆可运行，即它可以让美工在浏览器查看页面的静态效果，也可以让程序员在服务器查看带数据的动态页面效果。这是由于它支持 html 原型，然后在 html 标签里增加额外的属性来达到模板+数据的展示方式。浏览器解释 html 时会忽略未定义的标签属性，所以 thymeleaf 的模板可以静态地运行；当有数据返回到页面时，Thymeleaf 标签会动态地替换掉静态内容，使页面动态显示。

- 2.Thymeleaf 开箱即用的特性。它提供标准和spring标准两种方言，可以直接套用模板实现JSTL、 OGNL表达式效果，避免每天套模板、该jstl、改标签的困扰。同时开发人员也可以扩展和创建自定义的方言。

- 3.Thymeleaf 提供spring标准方言和一个与 SpringMVC 完美集成的可选模块，可以快速的实现表单绑定、属性编辑器、国际化等功能。
### thymeleaf 静态资源配置
```java
#设定文件路径
spring.thymeleaf.prefix=calsspath:/templates
#加载的文件后缀名
spring.thymeleaf.suffix=.html
spring.thymeleaf.mode=HTML5
spring.thymeleaf.encoding=UTF-8
spring.thymeleaf.content-type=text/html
#关闭缓存，及时刷新，上线生产环境改为true
spring.thymeleaf.cache=false
```
### 在pom.xml文件添加thymeleaf 项目依赖
```java
<dependency>
      <groupId>org.springframework.boot</groupId>
      <artifactId>spring-boot-starter-thymeleaf</artifactI>
</dependency>
```
### thymeleaf 常用标签使用方式
- 表达式
```java
${} ：变量表达式。
*{} ：选择/所有值表达式，比如*{name},先从可用值查找，如果有上下文，上层是Object,则查找Object中的name属性值。一般跟在th:object后，直接取object中的属性。
#{} ：消息表达式，国际化时使用，也可使用内置对象，如dates格式化数据。
@{} ：链接表达式，配合link,src,href使用，支持决定路径和相对路径。
~{} ：片段表达式，用来引入公共部分的代码片段，并进行传值操作。
```
- 对象引用方式
```java
  th:object="${对象}"
```
- 时间类型转换
```java
  th:value="${#dates.format(date,'yyyy-MM-dd')}"
           "${#dates.arrayFormat(dateArray,'yyyy-MM-dd')}"
           "${#dates.listFormat(dateList,'yyyy-MM-dd')}"
           "${#dates.setFormat(dateSet,'yyyy-MM-dd')}"
```
- th:text与th:utext区别
```java
  text是非转义文本，会原样输出引用的内容；
  utext是转义文本，会解析内容，然后输出；
  例：
  <p th:text="'Any characters, <br>Let\'s</br> go!'"></p>
  页面显示效果：
  Any characters, <br>Let's</br> go!

  <p th:utext="'Any characters, <br>Let's</br> go!'"></p>
  页面显示效果：
  Any characters, 
  Let's 
  go!
```
- URL
```java
  绝对路径：
  <a th:href="@{http://www.baidu.com}"></a>
  另外，如果需要Thymeleaf对URL进行渲染，那么务必使用th:href，th:src等属性，下面是一个例子
  <!-- Will produce 'http://localhost:8080/gtvg/order/details?orderId=3' (plus rewriting) -->
  <a href="details.html" th:href="@{http://localhost:8080/gtvg/order/details(orderId=${o.id})}">view</a>

  <!-- Will produce '/gtvg/order/details?orderId=3' (plus rewriting) -->
  <a href="details.html" th:href="@{/order/details(orderId=${o.id})}">view</a>

  <!-- Will produce '/gtvg/order/3/details' (plus rewriting) -->
  <a href="details.html" th:href="@{/order/{orderId}/details(orderId=${o.id})}">view</a>
  URL最后的(orderId=${o.id})表示将括号内的内容作为URL参数处理，该语法避免使用字符串拼接，大大提高了可读性
```
- 引入静态资源文件js/css
```java
  <script th:src="@{/static/js/test.js}"></script>
  在资源文件中添加配置：spring.mvc.static-path-pattern=/static/**
```
- 条件判断th:if/th:unless
```java
  <div th:if="${value}==18">等于18</div>
  <div th:if="${value} gt 18">大于18</div>
  <div th:if="${value} lt 18">小于18</div>
  <div th:if="${value} ge 18">大于等于18</div>
  <div th:if="${value} le 18">小于等于18</div>
  eq相等（equal）
  <div>标签只有在th:if中条件成立时才显示;
  th:unless于th:if恰好相反，只有表达式中的条件不成立，才会显示其内容
```
- 循环th:each
```java
  <tr th:each="person:${list}">
    <td th:text="${person.name}"></td>
    <td th:text="${person.age}"></td>
  </tr>
```
- th:switch和th:case多路选择
```java
  <div th:switch="${user.name}">
    <p th:case="'lee'">lee</p>
    <p th:case="#{roles.manager}"></p>
  </div>
```
- 内嵌变量
```java
  为了模板更加易用，Thymeleaf还提供了一系列Utility对象（内置于Context中），可以通过#直接访问：
      dates : java.util.Date的功能方法类。
      calendars : 类似#dates，面向java.util.Calendar
      numbers : 格式化数字的功能方法类
      strings : 字符串对象的功能类
      objects : 对objects的功能类操作。
      bools : 对布尔值求值的功能方法。
      arrays : 对数组的功能类方法。
      lists : 对lists功能类方法
      sets : 对sets功能类的方法
      maps : 对maps功能类的方法
```


