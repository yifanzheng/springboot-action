package com.example.jdbctemplate.service;

import com.example.jdbctemplate.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * UserService
 *
 * @author star
 */
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;


}
