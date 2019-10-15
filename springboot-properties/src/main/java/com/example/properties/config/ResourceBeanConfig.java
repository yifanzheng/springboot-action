package com.example.properties.config;

import com.example.properties.entity.ResourceBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * 配置类
 *
 * @author star
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
