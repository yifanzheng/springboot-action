package com.example.c3p0.controller;

import com.example.c3p0.domain.User;
import com.example.c3p0.service.DataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 控制层
 *
 * @author kevin
 * @date 2018-10-18 21:15
 **/
@RestController
@RequestMapping("/api")
public class DemoController {
    @Autowired
    private DataService dataService;

    @GetMapping("/users")
    public ResponseEntity<List<User>> getUsers(){
        return ResponseEntity.ok(dataService.getUsers());
    }

    @PostMapping("/user")
    public ResponseEntity<String> insertUser(@RequestBody User user){
        dataService.insertUser(user);
        return ResponseEntity.ok("success!");
    }
}
