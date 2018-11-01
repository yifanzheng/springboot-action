package com.example.jtaatomikos.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.jtaatomikos.commonservice.CommonService;
import com.example.jtaatomikos.domain.Book;
import com.example.jtaatomikos.domain.Player;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * DemoController
 *
 * @author kevin
 **/
@Controller
public class DemoController {
    @Autowired
    private CommonService service;

    @PostMapping("/api/insert")
    public ResponseEntity<Object> insert(@RequestBody String jsonStr) {
        JSONObject jsonObject = JSONObject.parseObject(jsonStr);

        Player player = JSONObject.parseObject(jsonObject.get("player").toString(),Player.class);
        Book book = JSONObject.parseObject(jsonObject.get("book").toString(), Book.class);

        int count = service.InserTData(player, book);
        return ResponseEntity.ok(count);
    }

}
