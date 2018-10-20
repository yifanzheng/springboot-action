package com.example.guava.config;

import com.google.common.cache.CacheBuilder;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.guava.GuavaCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

/**
 * Guava缓存配置类
 *
 * @author kevin
 * @date 2018-10-20 21:51
 **/
@Configuration
@EnableCaching
public class GuavaCacheConfig {

    /**
     * expireAfterAccess: 当缓存项在指定的时间段内没有被读或写就会被回收。
     * expireAfterWrite：当缓存项在指定的时间段内没有更新就会被回收,如果我们认为缓存数据在一段时间后数据不再可用，那么可以使用该种策略。
     * refreshAfterWrite：当缓存项上一次更新操作之后的多久会被刷新。
     * @return
     */
    @Bean
    public CacheManager cacheManager(){
        GuavaCacheManager cacheManager = new GuavaCacheManager();

        cacheManager.setCacheBuilder(CacheBuilder.newBuilder()
                // 缓存有效时间
                .expireAfterWrite(10, TimeUnit.SECONDS)
                .maximumSize(1000)
        );
        return cacheManager;
    }

}
