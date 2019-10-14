package com.example.demo.web.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * HelloWorldResource
 *
 * @author star
 */
@RestController
@RequestMapping("/api")
public class HelloWorldResource {

    @GetMapping("/hello")
    public ResponseEntity<String> helloWorld() {
        String message = "Hello World!";
        return ResponseEntity.ok(message);
    }

}
