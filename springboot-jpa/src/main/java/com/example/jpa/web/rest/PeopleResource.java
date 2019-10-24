package com.example.jpa.web.rest;

import com.example.jpa.service.PeopleService;
import com.example.jpa.service.dto.PeopleDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * PeopleResource
 *
 * @author star
 */
@RestController
@RequestMapping("/api")
public class PeopleResource {

    @Autowired
    private PeopleService peopleService;

    @PostMapping("/peoples")
    public ResponseEntity<Void> savePeople(@RequestBody PeopleDTO dto) {
        peopleService.savePeople(dto);
        return ResponseEntity.ok().build();
    }
}
