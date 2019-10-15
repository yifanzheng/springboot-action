package com.example.properties.entity;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * 资源实体类
 *
 * @author kevin
 * @date 2018-08-26 10:34
 **/
@Configuration
@ConfigurationProperties(prefix = "com.resource")
@PropertySource(value = "classpath:resource.properties")
public class ResourceConfig {
    private String name;

    private String website;

    private String language;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }
}
