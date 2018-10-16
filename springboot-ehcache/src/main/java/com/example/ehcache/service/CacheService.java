package com.example.ehcache.service;

import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

/**
 * 业务层
 *
 * @author kevin
 * @date 2018-10-16 20:46
 **/
@Service
@CacheConfig(cacheNames = "ehcache")
public class CacheService {

    @Cacheable(key = "#key")
    public String getContent(String key) {
        System.out.println("存入缓存了");
        return "star";
    }
}
