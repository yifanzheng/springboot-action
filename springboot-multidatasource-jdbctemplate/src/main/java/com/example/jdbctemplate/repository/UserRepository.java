package com.example.jdbctemplate.repository;

import com.example.jdbctemplate.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 * UserRepository
 *
 * @author star
 */
@Repository
public class UserRepository {

    @Autowired
    @Qualifier("JdbcTemplateTwo")
    private JdbcTemplate jdbcTemplate;

    public int insertUser(User user) {
        String sql = "INSERT INTO user(username, password) VALUES(?,?)";
        int count = jdbcTemplate.update(sql, user.getUsername(), user.getPassword());
        return count;

    }
}
