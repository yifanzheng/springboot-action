package com.example.async.web;

import com.example.async.service.AsyncService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 控制器
 *
 * @author kevin
 * @date 2018-10-21 22:45
 **/
@RestController
@RequestMapping("/api")
public class DemoController {

    @Autowired
    private AsyncService service;

    @GetMapping("/getContent")
    public String getContent(){
        System.out.println("1");
        // 调用方法会异步执行
        String result = service.getContent();

        System.out.println("4");
        return "result:"+result;
    }
}
