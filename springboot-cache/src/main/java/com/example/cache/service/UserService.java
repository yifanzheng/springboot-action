package com.example.cache.service;

import com.example.cache.dto.UserDto;
import com.example.cache.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * UserService
 *
 * @author star
 **/
@Service
@CacheConfig(cacheNames = "users")// 指定缓存名称，在本类中是全局的
public class UserService {

    @Autowired
    private UserRepository userRepository;

    /**
     * 缓存 key 是 username 的数据到缓存 users 中，
     * 如果没有指定 key，则方法参数作为 key 保存到缓存中
     */
    @Cacheable(key = "#username")
    public UserDto getUser(String username) {
        System.out.println("从数据库中获取数据，而不是读取缓存");
        return userRepository.getUser(username);
    }


    /**
     * 新增或更新缓存中的数据
     */
    @CachePut(key = "#username")
    public List<UserDto> save(String username) {
        return userRepository.save(username);
    }

    /**
     * 从缓存 users 中删除 key 是 username 的数据
     */
    @CacheEvict(key = "#username")
    public List<UserDto> deleteUser(String username) {
        System.out.println("从数据库中删除数据，以及缓存中的数据");
        return userRepository.deleteUser(username);
    }
}
