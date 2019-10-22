package com.example.jdbctemplate.service;

import com.example.jdbctemplate.entity.Product;
import com.example.jdbctemplate.repository.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * ProductService
 *
 * @author star
 */
@Service
public class ProductService {

    private static final Logger logger = LoggerFactory.getLogger(ProductService.class);

    @Autowired
    private ProductRepository productRepository;

    public void insertProduct(Product product) {
        productRepository.insertProduct(product);
        logger.info("Insert product success");
    }
}
