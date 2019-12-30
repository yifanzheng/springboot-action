package com.example.guava.web.rest;

import com.example.guava.dto.UserDto;
import com.example.guava.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * UserResource
 *
 * @author star
 **/
@RestController
@RequestMapping("/api")
public class UserResource {

    @Autowired
    private UserService userService;

    @Autowired
    private CacheManager cacheManager;

    @GetMapping("/users/{name}")
    public ResponseEntity<UserDto> getUser(@PathVariable String name) {
        System.out.println("==================");
        UserDto user = userService.getUserByName(name);
        System.out.println(cacheManager.toString());
        return ResponseEntity.ok(user);
    }
}
