package com.example.thymeleaf.controller;

import com.example.thymeleaf.pojo.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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

    @GetMapping("/test")
    public String test(ModelMap map){
        map.addAttribute("user",user);
        return "test";
    }
}
