package com.example.log4j.web;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 控制器
 *
 * @author kevin
 * @date 2018-10-21 17:20
 **/
@RestController
@RequestMapping("/api")
public class DemoController {

    @RequestMapping("/getMember")
    public String getMember(String name, int age) {
        return "success!";
    }
}
