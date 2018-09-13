package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Auther: sz7v
 * @Date: 2018/8/24 16:34
 * @Description:
 */
@RestController
public class DemoController {

    @Autowired
    private StringRedisTemplate redisTemplate;

    @RequestMapping("/user")
    public String getUser() {
        //redisTemplate.opsForValue().set("name","kevin");
        String mykey =(String) redisTemplate.opsForValue().get("mykey");
        System.out.println(mykey);
        return mykey;

    }
}
