package com.example.mybatis.mapper.secondary;

import com.example.mybatis.entity.Product;

import java.util.List;

/**
 * ProductMapper
 *
 * @author star
 */
public interface ProductMapper {

  List<Product> findAllProduct();

  void insertProduct(Product product);
}
