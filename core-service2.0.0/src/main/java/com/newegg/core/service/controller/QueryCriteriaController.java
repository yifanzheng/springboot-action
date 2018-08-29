package com.newegg.core.service.controller;

import com.alibaba.fastjson.JSONObject;
<<<<<<< HEAD:core-service1.0.0/src/main/java/com/newegg/core/service/controller/QueryCriteriaController.java
import com.newegg.core.service.cache.QinCache;
=======
import com.newegg.core.service.cache.Cache;
>>>>>>> a42263d315a06cc0870010fa7c08f83323cc5b63:core-service2.0.0/src/main/java/com/newegg/core/service/controller/QueryCriteriaController.java
import com.newegg.core.service.domain.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * 缓存用户的查询条件
 */
@RestController
public class QueryCriteriaController {
    @Autowired
    private Cache cache;

    @GetMapping("/queryCriteriaList")
    public Query getQueryCriteriaList(){
        return cache.getQueryCriteria();
    }

    @PutMapping("/queryCriteriaList")
    public void saveQueryCriteriaList(@RequestBody Query query){
        cache.saveQueryCriteria(query);
    }
}
