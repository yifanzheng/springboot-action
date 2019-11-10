package com.example.mybatis;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * MyBatis 启动时可以不在 mapper 层加 @Mapper 注解，但是一定要在启动类上加上 @MapperScan 注解，并指定扫包范围。
 * 如果在 mapper 接口类上加上了 @Mapper 注解，就不需要在启动类上加 @MapperScan 注解了。
 */
@SpringBootApplication
@MapperScan(basePackages = "com.example.mybatis.mapper")
public class MybatisApplication {

    public static void main(String[] args) {
        SpringApplication.run(MybatisApplication.class, args);
    }
}
