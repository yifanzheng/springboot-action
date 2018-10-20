package com.example.guava.controller;

import com.example.guava.service.CacheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.lang.model.element.VariableElement;

/**
 * 控制器
 *
 * @author kevin
 * @date 2018-10-20 21:47
 **/
@RestController
@RequestMapping("/api")
public class DemoController {

    @Autowired
    private CacheService service;

    @PostMapping("/cache")
    public ResponseEntity<Object> save(){
        long time = service.saveCache();

        return ResponseEntity.ok(time);
    }

    @DeleteMapping("/cache")
    public ResponseEntity<Object> delete(){
        service.deleteCache();
        return ResponseEntity.ok("success!");
    }

    @GetMapping("/cache")
    public ResponseEntity<Object> getCache(){
        long cache = service.getCache();
        return ResponseEntity.ok(cache);
    }

}
