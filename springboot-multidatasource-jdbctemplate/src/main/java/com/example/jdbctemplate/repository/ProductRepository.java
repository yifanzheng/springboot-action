package com.example.jdbctemplate.repository;

import com.example.jdbctemplate.entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 * ProductRepository
 *
 * @author star
 */
@Repository
public class ProductRepository {

    @Autowired
    @Qualifier("JdbcTemplateTwo")
    private JdbcTemplate jdbcTemplate;

    public Integer insertProduct(Product product) {
        String sql = "INSERT INTO product(product_name, price, address) VALUES(?,?,?)";
        int count = jdbcTemplate.update(sql, product.getProductName(), product.getPrice(), product.getAddress());
        return count;

    }

}
