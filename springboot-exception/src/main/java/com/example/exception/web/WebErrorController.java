package com.example.exception.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 控制器
 *
 * @author kevin
 * @date 2018-09-07 20:50
 **/
@Controller
@RequestMapping("err")
public class WebErrorController {

    @RequestMapping("/error0")
    public String error(){
        int a=1/0;

        return "thymeleaf/error";
    }

    @RequestMapping("/error1")
    @ResponseBody // 返回json格式
    public String error1(@RequestParam int i){
        int a=1/i;
        return "success"+a;
    }
}
