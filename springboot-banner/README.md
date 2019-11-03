## Spring Boot 定制 Banner

Spring Boot 启动时会有默认的 Banner 被加载，但 Spring Boot 也支持自定义 Banner 功能。

### 自定义 Banner

只需在 resources 目录下放置名为 banner.txt、banner.gif 、banner.jpg 或 banner.png 的文件，Spring Boot 在启动时会自动加载文件中的内容作为 Banner。

如果是文本文件，Spring Boot 会将其直接输出。 

如果是图像文件（ banner.gif 、banner.jpg 或 banner.png ），Spring Boot 会将图像转为 ASCII 字符，然后输出。

### Banner 图形生成地址

[https://devops.datenkollektiv.de/banner.txt/index.html](https://devops.datenkollektiv.de/banner.txt/index.html)

[http://www.network-science.de/ascii/](http://www.network-science.de/ascii/)