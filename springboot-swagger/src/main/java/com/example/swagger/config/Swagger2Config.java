package com.example.swagger.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Swagger2Config
 *
 * @author star
 */
@Configuration
@EnableSwagger2
@ConditionalOnProperty(value = "swagger.enabled", havingValue = "true")
public class Swagger2Config {

    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                // 控制层路径，即 API 接口文件存放位置
                .apis(RequestHandlerSelectors.basePackage("com.example.swagger.web.rest"))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Spring Boot Swagger2")
                // 联系人信息
                .contact(new Contact("Star.Y.Zheng", "http://yifanstar.top/", "zhengyifan1996@outlook.com"))
                .version("1.0")
                .build();
    }

}
