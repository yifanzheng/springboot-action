package com.example.mybatis.web.rest;

import com.example.mybatis.service.PeopleService;
import com.example.mybatis.entity.People;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * PeopleResource
 *
 * @author star
 **/
@RestController
@RequestMapping("/api")
public class PeopleResource {

    @Autowired
    private PeopleService peopleService;



    @GetMapping("/peoples")
    public ResponseEntity<People> getPlayer(@RequestParam String name) {
        People people = peopleService.selectPeople(name);
        return ResponseEntity.ok(people);
    }

    @PostMapping("/players")
    public ResponseEntity<Void> insertPlayer(@RequestBody People people) {
        int i = peopleService.insertPlayer(people);
        return ResponseEntity.ok().build();
    }

}
