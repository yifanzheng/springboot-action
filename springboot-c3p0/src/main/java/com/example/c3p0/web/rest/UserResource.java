package com.example.c3p0.web.rest;

import com.example.c3p0.domain.User;
import com.example.c3p0.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 控制层
 *
 * @author star
 **/
@RestController
@RequestMapping("/api")
public class UserResource {

    @Autowired
    private UserService userService;

    @GetMapping("/users")
    public ResponseEntity<List<User>> getUsers(){
        return ResponseEntity.ok(userService.getUsers());
    }

    @PostMapping("/user")
    public ResponseEntity<String> insertUser(@RequestBody User user){
        userService.insertUser(user);
        return ResponseEntity.ok("success!");
    }
}
