package com.example.config;

import com.example.pojo.ResourceBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * 配置类
 *
 * @author kevin
 * @date 2018-08-26 15:44
 **/
@Configuration
@PropertySource(value = "classpath:resource.properties")
public class ResourceBeanConfig {

    @Bean
    @ConfigurationProperties(prefix = "com.resource")
    public ResourceBean getResourceBean(){
       return new ResourceBean();
    }
}
