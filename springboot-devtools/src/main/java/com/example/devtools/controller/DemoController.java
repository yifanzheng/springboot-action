package com.example.devtools.controller;

import com.example.devtools.pojo.User;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 控制器
 *
 * @author kevin
 * @date 2018-08-26 21:56
 **/
@RestController
public class DemoController {

    @RequestMapping("/user")
    public User getUser(){
        User user = new User();
        user.setName("kevin12");
        user.setAge("21");
        user.setDesc("Hi all,my name is kevin!");
        return user;
    }
}
