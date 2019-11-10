package com.example.mybatis.service;

import com.example.mybatis.entity.Product;
import com.example.mybatis.mapper.secondary.ProductMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * ProductService
 *
 * @author star
 */
@Service
public class ProductService {

    @Autowired
    private ProductMapper productMapper;

    public List<Product> listProduct() {
        List<Product> allProduct = productMapper.findAllProduct();

        return allProduct;
    }

    public void insertProduct(Product product) {
        productMapper.insertProduct(product);
    }
}
