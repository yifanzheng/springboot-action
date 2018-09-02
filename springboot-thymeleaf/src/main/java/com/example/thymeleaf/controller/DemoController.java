package com.example.thymeleaf.controller;

import com.example.thymeleaf.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;

/**
 * 控制器
 *
 * @author kevin
 * @date 2018-08-29 20:34
 **/
@Controller
@RequestMapping("/th")
public class DemoController {

    @Autowired
    private User user;

    @GetMapping("/index")
    public String index(ModelMap map){
        map.addAttribute("name","thymeleaf-imooc");
        map.addAttribute("content","<font color='green'>kevin</font>");
        return "index";
    }

    @GetMapping("/center")
    public String center(){
        return "center";
    }

    @RequestMapping("/test")
    public String test(ModelMap map){
        user.setBirthday(new Date());
        map.addAttribute(user);
        User user1 = new User();
        user1.setName("lee");
        user1.setAge(18);
        user1.setDesc("嗨，哈哈哈哈");
        User user2 = new User();
        user2.setName("eric");
        user2.setAge(20);
        user2.setDesc("ffffff");
        ArrayList<User> userList = new ArrayList<>();
        userList.add(user);
        userList.add(user1);
        userList.add(user2);
        map.addAttribute("userList", userList);
        return "test";
    }

    @PostMapping("/postform")
    public String postFrom(User user){
        System.out.println(user.getName());
        return "redirect:/th/test";
    }

}
