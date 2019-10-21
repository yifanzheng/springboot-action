package com.example.jdbctemplate.web.rest;

import com.example.jdbctemplate.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * ProductResource
 *
 * @author star
 */
@RestController
@RequestMapping("/api")
public class ProductResource {

    @Autowired
    private ProductService productService;




}
