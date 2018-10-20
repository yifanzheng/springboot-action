package com.example.guava.service;

import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;

/**
 * 缓存逻辑
 *
 * @author kevin
 * @CacheConfig(cacheNames = "guavaCache") 声明缓存的名称
 * @date 2018-10-20 22:05
 **/
@Service
public class CacheService {

    @CachePut(value = "guavaCache")
    public long saveCache() {
        long time = new Timestamp(System.currentTimeMillis()).getTime();
        System.out.println("已缓存了");
        return time;
    }

    @CacheEvict(value = "guavaCache")
    public void deleteCache() {
        System.out.println("删除缓存了");
    }

    @Cacheable(value = "guavaCache")
    public long getCache() {
        long time = new Timestamp(System.currentTimeMillis()).getTime();
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("缓存了");
        return time;
    }

}
