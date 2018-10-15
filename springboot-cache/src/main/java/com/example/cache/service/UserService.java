package com.example.cache.service;

import com.example.cache.dto.UserDto;
import com.example.cache.repostory.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 业务层
 *
 * @author kevin
 * @date 2018-10-14 22:44
 **/
@Service
public class UserService {

    @Autowired
    private UserRepo userRepo;

    public UserDto getUser(String username) {
        return userRepo.getUser(username);
    }

    public List<UserDto> save(String username) {
        return userRepo.save(username);
    }

    public List<UserDto> deleteUser(String username) {
        return userRepo.deleteUser(username);
    }
}
