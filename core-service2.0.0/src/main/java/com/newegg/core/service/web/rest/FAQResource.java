package com.newegg.core.service.web.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FAQResource {
    
    @GetMapping("/faq.htm")
    public ResponseEntity<String> getFAQ() {
        return ResponseEntity.ok("<!--Newegg-->");
    }
    
}