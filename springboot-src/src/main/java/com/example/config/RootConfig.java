package com.example.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * 根配置信息
 *
 * 加载根目录下的所有类到spring容器中
 *
 * @author kevin
 **/
@Configuration
@ComponentScan(basePackages = {"com.example"})
public class RootConfig {
}
