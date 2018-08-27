package com.example.pojo;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * 实体类
 *
 * @author kevin
 * @date 2018-08-26 9:21
 **/
@Configuration
//annotated with @ConfigurationProperties by using the spring-boot-configuration-processor.jar
@ConfigurationProperties(prefix = "user")
@PropertySource(value = "classpath:user.properties")
public class User {
    private String id;

    private String name;

    private int age;

    private String desc;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
