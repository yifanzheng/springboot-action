package com.example.jdbctemplate.service;

import com.example.jdbctemplate.entity.User;
import com.example.jdbctemplate.repositry.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public User getUser(String username) {
        User user = userRepository.getUser(username);

        return user;
    }

    public List<User> listUser() {
        List<User> users = userRepository.listUser();

        return users;
    }

    public void saveOrUpdateUser(User user) {
        userRepository.saveOrUpdateUser(user);
        logger.debug("Update user success");
    }

    public void deleteUser(String username) {
        userRepository.deleteUser(username);
        logger.debug("Delete user for username: {}", username);
    }
}
