package com.example.resource.controller;

import com.example.resource.datasource01.service.DataSourceService1;
import com.example.resource.datasource02.service.DataSourceService2;
import com.example.resource.domain.Book;
import com.example.resource.domain.Player;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * 多数据源测试
 *
 * @author kevin
 **/
@RestController
@RequestMapping("/api")
public class DemoController {

    @Autowired
    private DataSourceService1 service1;

    @Autowired
    private DataSourceService2 service2;

    @GetMapping("/player")
    public ResponseEntity<Player> queryPlayer(@RequestParam String name) {
        Player player = service1.selectPlayer(name);
        return ResponseEntity.ok(player);
    }

    @PostMapping("/player")
    public ResponseEntity<Object> insertPlayer(@RequestBody Player player) {
        int i = service1.insertPlayer(player);
        return ResponseEntity.ok(String.format("Insert %d data successed !",i));
    }

    @GetMapping("/book")
    public ResponseEntity<Book> queryBook(@RequestParam String name) {
        Book book = service2.selectPlayer(name);
        return ResponseEntity.ok(book);
    }

    @PostMapping("/book")
    public ResponseEntity<Object> insertBook(@RequestBody Book book){
        int i = service2.insertPlayer(book);
        return ResponseEntity.ok(String.format("Insert %d data successed !",i));
    }
}
