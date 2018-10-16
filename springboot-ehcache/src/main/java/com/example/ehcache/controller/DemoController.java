package com.example.ehcache.controller;

import com.example.ehcache.service.CacheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 控制器
 *
 * @author kevin
 * @date 2018-10-16 20:38
 **/
@RestController
public class DemoController {

    @Autowired
    private CacheService cacheService;

    @Autowired
    private EhCacheCacheManager cacheManager;

    @GetMapping("/getContent")
    public ResponseEntity<String> getContent(@RequestParam(value = "key") String key) {
        System.out.println("调用了api");
        String content = cacheService.getContent(key);
        return ResponseEntity.ok(content);
    }
}
