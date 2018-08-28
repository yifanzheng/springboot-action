package com.newegg.core.service.cache;


import com.alibaba.fastjson.JSONObject;
import com.newegg.core.service.domain.Record;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class RedisUtil {

    @Autowired
    private RedisTemplate<Object,Object> redisTemplate;


    public Boolean hasKey(String key) {
        return redisTemplate.hasKey(key);
    }

    public ArrayList get(String h) {
        if (h == null || !redisTemplate.hasKey(h)) {
            return null;
        }
        return (ArrayList) redisTemplate.opsForHash().values(h);
    }

    public Object get(String h,String hk){
        return redisTemplate.opsForHash().get(h,hk);
    }


    /**
     * 设置永久有效的缓存
     * @param h hash表的名称
     * @param hk hash表中的键
     * @param hv hash表中的值
     */
    public void set(String h, String hk,Object hv) {
        redisTemplate.opsForHash().put(h,hk,hv);
    }

//    /**
//     * 设置带时间的缓存
//     *
//     * @param key    键值名称
//     * @param value  键值
//     * @param offset 失效时间 要大于0 如果time小于等于0 将设置无限期
//     */
//    public void set(String key, Object value, long offset) {
//        redisTemplate.opsForValue().set(key, value, offset);
//    }

    public void remove(String key) {
        if (key != null && redisTemplate.hasKey(key)) {
            redisTemplate.delete(key);
        }
    }
}

