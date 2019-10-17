package com.example.properties.web.rest;

import com.example.properties.config.AuthorBeanConfig;
import com.example.properties.config.AuthorConfig;
import com.example.properties.config.RandomConfig;
import com.example.properties.config.UserConfig;
import com.example.properties.entity.Author;
import com.example.properties.entity.AuthorBean;
import com.example.properties.entity.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * PropertiesResource
 *
 * @author star
 **/
@RestController
@RequestMapping("/api/config")
public class PropertiesResource {

    @Autowired
    private RandomConfig randomConfig;

    @Autowired
    private AuthorConfig authorConfig;

    @Autowired
    private AuthorBeanConfig authorBeanConfig;

    @Autowired
    private UserConfig userConfig;

    @GetMapping("/random")
    public ResponseEntity<RandomConfig> getRandomConfig() {
        return ResponseEntity.ok(randomConfig);
    }

    @GetMapping("/author")
    public ResponseEntity<Author> getAuthorConfig() {
        Author author = new Author();
        BeanUtils.copyProperties(authorConfig, author);
         /*注意：直接在类上使用 @Configuration、@ConfigurationProperties(prefix = "com.resource")进行配置时,
         不能直接返回 resourceConfig，它只是指向 Spring 容器中 AuthorConfig 对象资源的一个标识，
         可以通过这个标识返回该对象中的值，但不能直接返回该标识，可通过上面的方式返回该对象*/
        return ResponseEntity.ok(author);
    }

    @GetMapping("/authorBean")
    public ResponseEntity<AuthorBean> getAuthorBean() {
        AuthorBean authorBean = new AuthorBean();
        BeanUtils.copyProperties(authorBeanConfig, authorBean);
        return ResponseEntity.ok(authorBean);

    }

    @GetMapping("/user")
    public ResponseEntity<User> getUserConfig() {
        User user = new User();
        BeanUtils.copyProperties(userConfig, user);
        return ResponseEntity.ok(user);
    }
}
