package com.example.mybatis.web.rest;

import com.example.mybatis.entity.Product;
import com.example.mybatis.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping("/products")
    public ResponseEntity<List<Product>> listProduct() {
        List<Product> products = productService.listProduct();

        return ResponseEntity.ok(products);
    }

    @PostMapping("/products")
    public ResponseEntity<Void> saveProduct(@RequestBody Product product){
        productService.insertProduct(product);

        return ResponseEntity.ok().build();
    }
}
