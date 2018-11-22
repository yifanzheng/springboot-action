package com.example.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * springMvc配置信息
 * @author kevin
 **/
@Configuration
@EnableWebMvc
@ComponentScan(basePackages = {"com.example.controller"})
public class WebConfig extends WebMvcConfigurerAdapter {

}
