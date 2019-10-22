package com.example.jdbctemplate.service;

import com.example.jdbctemplate.entity.User;
import com.example.jdbctemplate.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * UserService
 *
 * @author star
 */
@Service
public class UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private UserRepository userRepository;

    public void insertUser(User user){
        userRepository.insertUser(user);
        logger.info("Insert user success");
    }
}
