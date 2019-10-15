package com.example.properties.web.rest;

import com.example.properties.entity.ResourceBean;
import com.example.properties.entity.ResourceComponent;
import com.example.properties.entity.ResourceConfig;
import com.example.properties.entity.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 控制器
 *
 * @author star
 **/
@RestController
@RequestMapping("/api")
public class PropertiesResource {

    @Autowired
    private ResourceComponent resourceComponent;

    @Autowired
    private ResourceConfig resourceConfig;

    @Autowired
    private ResourceBean resourceBean;

    @Autowired
    private User user;

    @RequestMapping("/component")
    public ResourceComponent getResourceComponent() {
        /*ResourceComponent bean= new ResourceComponent();
        BeanUtils.copyProperties(resourceComponent,bean);*/
        return resourceComponent;
    }

    @RequestMapping("/config")
    public ResourceConfig getResourceConfig(){
        ResourceConfig bean = new ResourceConfig();
        BeanUtils.copyProperties(resourceConfig,bean);
         /*注意：直接在类上使用@Configuration、@ConfigurationProperties(prefix = "com.resource")进行配置时,
         不能直接返回resourceConfig，它只是指向spring容器中resourceConfig对象资源的一个标识，
         可以通过这个标识返回该对象中的值，但不能直接返回该标识，可通过上面的方式返回该对象*/
        //return resourceConfig;
        return bean;
    }

    @RequestMapping("/bean")
    public ResourceBean getResourceBean(){
        return resourceBean;
    }

    @RequestMapping("/user")
    public User getUser(){
        User bean = new User();
        BeanUtils.copyProperties(user,bean);
        return bean;
    }
}
