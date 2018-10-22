package com.example.mybatis.controller;

import com.example.mybatis.domain.Users;
import com.example.mybatis.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * 控制器
 * 测试mybatis整合springboot
 * @author kevin
 * @date 2018-10-22 10:58
 **/
@RestController
@RequestMapping("/api")
public class DemoController {

    @Autowired
    private UserService service;

    @GetMapping("/getUser")
    public ResponseEntity<Users> getUser(String name){
        Users user = service.getUser(name);
        return ResponseEntity.ok(user);
    }

    @PostMapping("/insertUser")
    public ResponseEntity<Object> insertUser(@RequestBody Users user){
        int row = service.insertUser(user.getName(), user.getPassword());
        return ResponseEntity.ok(row+"行添加成功");
    }
}
