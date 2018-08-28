package com.newegg.core.service.cache;

import com.newegg.core.service.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * by:qin
 */
@Component
public class Cache {
    @Autowired
    private RedisUtil redisUtil;

    public <T extends Record> void saveCache(CacheKey target, ArrayList<T> records) {
        HashMap<String, Record> map = new HashMap<>();
        records.forEach(e -> map.put(e.getId() + "", e));
        redisUtil.set(CacheKey.ProblemRecord.toString(), map);
    }


    /**
     * 获得缓存，但不保证是最新的
     *
     * @param <T>
     * @param target 哪一个单据的缓存
     * @return
     */
    public <T> ArrayList<T> getCache(CacheKey target) {
        return redisUtil.get(target.toString());
    }

    public boolean IsAlreadyCache(CacheKey target) {
        return redisUtil.hasKey(target.toString());
    }

    /**
     * 获得用户以前点击的查询条件
     *
     * @return
     */
    public Query getQueryCriteria() {
        Query query = (Query) redisUtil.get(CacheKey.QqueryCriteria + "", 1 + "");//hk值用1
        return query;
    }

    /**
     * 缓存查询条件
     *
     * @param query
     */
    public void saveQueryCriteria(Query query) {
        redisUtil.set(CacheKey.QqueryCriteria + "", 1 + "", query);
    }

    public void deleteCache(CacheKey target) {
        if (IsAlreadyCache(target))
            redisUtil.remove(target.toString());
    }
}
