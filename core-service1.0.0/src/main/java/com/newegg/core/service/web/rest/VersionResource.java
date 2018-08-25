package com.newegg.core.service.web.rest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class VersionResource {

    @Value("${env}")
    private String env;

    @GetMapping("/version")
    public ResponseEntity<String> getVersion() {
        return ResponseEntity.ok(env + " - 1.0.0");
    }
    
}