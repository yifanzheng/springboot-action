package com.example.properties.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * AuthorConfig
 *
 * @author star
 **/
@Configuration
@ConfigurationProperties(prefix = "author")
@PropertySource(value = "classpath:author.properties")
public class AuthorConfig {

    private String name;

    private String nickname;

    private String intro;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
}
