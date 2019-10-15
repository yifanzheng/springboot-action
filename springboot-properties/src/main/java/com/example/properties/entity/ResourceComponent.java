package com.example.properties.entity;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * 资源实体类
 *
 * @author kevin
 * @date 2018-08-26 10:34
 **/
@Component
@PropertySource(value = "classpath:resource.properties")
public class ResourceComponent {
    @Value("${com.resource.name}")
    private String name;

    @Value("${com.resource.website}")
    private String website;

    @Value("${com.resource.language}")
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
