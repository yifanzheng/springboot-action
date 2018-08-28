package com.example.controller;

import com.example.pojo.ResourceBean;
import com.example.pojo.ResourceComponent;
import com.example.pojo.ResourceConfig;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 控制器
 *
 * @author kevin
 * @date 2018-08-26 10:33
 **/
@RestController
@RequestMapping("pojo")
public class DemoController {
    @Autowired
    private ResourceComponent resourceComponent;

    @Autowired
    private ResourceConfig resourceConfig;

    @Autowired
    ResourceBean resourceBean;

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
}
