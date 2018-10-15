package com.example.cache.controller;

import com.example.cache.dto.UserDto;
import com.example.cache.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 控制器
 *
 * @author kevin
 * @date 2018-10-14 21:00
 **/
@RestController
@RequestMapping("/user")
public class DemoController {

    @Autowired
    private UserService userService;

    @GetMapping("/{username}")
    public ResponseEntity<UserDto> getUser(@PathVariable String username){

        // 获取数据
        UserDto user = userService.getUser(username);
        System.out.println("获取了user");

        return ResponseEntity.ok(user);
    }

    @PutMapping("/{username}")
    public ResponseEntity<List<UserDto>> save(@PathVariable String username){
        List<UserDto> userDtoList = userService.save(username);

        return ResponseEntity.ok(userDtoList);
    }

    @DeleteMapping("/{username}")
    public ResponseEntity<List<UserDto>> delete(@PathVariable String username){
        List<UserDto> userDtoList = userService.deleteUser(username);

        return ResponseEntity.ok(userDtoList);
    }
}
