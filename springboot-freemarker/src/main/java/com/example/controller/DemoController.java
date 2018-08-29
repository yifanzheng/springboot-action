package com.example.controller;

import com.example.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 控制层
 *
 * @author kevin
 * @date 2018-08-26 9:32
 **/
@Controller
@RequestMapping("ftl")
public class DemoController {

    @Autowired
    private User user;

    @RequestMapping("/index")
    public String index(){
        //访问静态的页面
        return "index";
    }

    @RequestMapping("/center")
    public String center(ModelMap map){
        //ModelMap相当于springMvc中的ModelAndView
        map.addAttribute("user",user);
        //访问动态的页面
        return "center";
    }
}
