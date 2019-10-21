package com.example.jdbctemplate.service;

import com.example.jdbctemplate.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * ProductService
 *
 * @author star
 */
@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;


}
