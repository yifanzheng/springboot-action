package com.example.log4j.web.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Log4jResource
 *
 * @author star
 **/
@RestController
@RequestMapping("/api")
public class Log4jResource {

    private static final Logger logger = LoggerFactory.getLogger(Log4jResource.class);

    @GetMapping("/testLogging")
    public String testLogging() {

        return "success!";
    }
}
