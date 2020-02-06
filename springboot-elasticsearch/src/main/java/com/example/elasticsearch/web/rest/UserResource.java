package com.example.elasticsearch.web.rest;

import com.example.elasticsearch.entity.User;
import com.example.elasticsearch.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * UserResource
 *
 * @author star
 */
@RestController
@RequestMapping("/api")
public class UserResource {

    @Autowired
    private UserService userService;

    @PostMapping("/users")
    public ResponseEntity<Void> saveUser(@RequestBody User user) {
        userService.saveUser(user);

        return ResponseEntity.ok().build();
    }

    @PostMapping("/users/batch")
    public ResponseEntity<Void> saveBatchUser(@RequestBody List<User> users) {
        userService.saveAllUser(users);

        return ResponseEntity.ok().build();
    }


    @GetMapping("/users/{id}")
    public ResponseEntity<User> getUser(@PathVariable("id") String id) {
        User user = userService.getUser(id);

        return ResponseEntity.ok(user);

    }

    @GetMapping("/users")
    public ResponseEntity<List<User>> searchUserByName(@RequestParam String name) {
        List<User> users = userService.searchUserByName(name);

        return ResponseEntity.ok(users);
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable("id") String id) {
        userService.deleteUser(id);

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/user/{name}")
    public ResponseEntity<Void> deleteUserByName(@PathVariable("name") String name) {
        userService.deleteUserByName(name);

        return ResponseEntity.ok().build();

    }
}
