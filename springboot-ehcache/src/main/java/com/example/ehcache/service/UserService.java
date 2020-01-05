package com.example.ehcache.service;

import com.example.ehcache.dto.UserDTO;
import com.example.ehcache.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

/**
 * UserService
 *
 * @author star
 **/
@Service
@CacheConfig(cacheNames = "user")
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Cacheable(key = "#name")
    public UserDTO getUser(String name) {
        System.out.println("从数据库中获取数据，而不是读取缓存");
        UserDTO user = userRepository.getUserByName(name);

        return user;
    }
}
