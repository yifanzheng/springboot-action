package com.example.exception.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 控制器
 *
 * @author kevin
 * @date 2018-09-07 20:50
 **/
@Controller
@RequestMapping("err")
public class WebErrorController {

    @RequestMapping("/error")
    public String error(){
        int a=1/0;

        return "thymeleaf/error";
    }
}
