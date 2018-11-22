package com.example.controller;

import com.example.service.IndexService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * IndexController
 *
 * @author kevin
 **/
@RestController
public class IndexController {

    @Autowired
    private IndexService service;

    @RequestMapping(value = "/index",produces = "text/html;charset=utf-8")
    public ResponseEntity<String> getContent() {
        String content = service.getContent();
        return ResponseEntity.ok(content);
    }

}
