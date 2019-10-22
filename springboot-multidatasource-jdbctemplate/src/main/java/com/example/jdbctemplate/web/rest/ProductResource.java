package com.example.jdbctemplate.web.rest;

import com.example.jdbctemplate.entity.Product;
import com.example.jdbctemplate.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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

    @PostMapping("/products")
    public ResponseEntity<Void> saveProduct(@RequestBody Product product) {
        productService.insertProduct(product);
        return ResponseEntity.ok().build();
    }

}
