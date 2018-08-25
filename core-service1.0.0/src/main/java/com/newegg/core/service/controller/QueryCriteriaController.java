package com.newegg.core.service.controller;

import com.alibaba.fastjson.JSONObject;
import com.newegg.core.service.cache.QinCache;
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
    private QinCache cache;

    @GetMapping("/queryCriteriaList")
    public Query getQueryCriteriaList(){
        return cache.getQueryCriteria();
    }

    @PutMapping("/queryCriteriaList")
    public void saveQueryCriteriaList(@RequestBody Query query){
        cache.saveQueryCriteria(query);
    }
}
