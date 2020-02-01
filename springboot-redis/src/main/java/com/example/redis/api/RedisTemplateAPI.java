package com.example.redis.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * Redis 操作 API
 *
 * @author star
 */
@Service
public class RedisTemplateAPI {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    /**
     * 获取指定 key 的缓存过期时间，单位：秒
     *
     * @param key 键，不能为 null
     * @return 返回给定 key 的剩余生存时间，返回 0 代表永久有效
     */
    public long getExpire(String key) {

        return redisTemplate.getExpire(key);
    }

    /**
     * 设置缓存过期时间，单位秒
     *
     * @param key     键
     * @param timeout 过期时间，单位：秒
     */
    public void expire(String key, long timeout) {
        if (timeout > 0) {
            redisTemplate.expire(key, timeout, TimeUnit.SECONDS);
        }
    }

    /**
     * 对 key 进行自增
     *
     * @param key   键
     * @param delta 自增的数量
     * @return 返回当前数
     */
    public long increment(String key, long delta) {
        return redisTemplate.opsForValue().increment(key, delta);
    }

    /**
     * 判断 key 是否存在
     *
     * @param key 键
     * @return true 表示存在，false 表示不存在
     */
    public boolean hasKey(String key) {
        return redisTemplate.hasKey(key);
    }

    /**
     * 查找所有符合给定模式 pattern 的 key
     */
    public Set<String> keys(String pattern) {
        return redisTemplate.keys(pattern);
    }

    /**
     * 删除指定 key 的缓存
     *
     * @param key 键
     */
    public void delete(String key) {
        redisTemplate.delete(key);
    }

    /**
     * 存入缓存（将字值 value 关联到 key）
     *
     * @param key   键
     * @param value 缓存值
     */
    public void setValue(String key, Object value) {
        redisTemplate.opsForValue().set(key, value);
    }

    /**
     * 存入缓存值并设置过期时间（秒）
     *
     * @param key     键
     * @param value   缓存值
     * @param timeout 过期时间（以秒为单位）
     */
    public void setValue(String key, Object value, long timeout) {
        redisTemplate.opsForValue().set(key, value, timeout, TimeUnit.SECONDS);
    }

    /**
     * 根据 key 获取缓存值
     *
     * @param key 键
     */
    public Object getValue(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    /**
     * 存入值到哈希表缓存中
     *
     * @param key   键
     * @param value 值
     */
    public void hashSet(String key, Map<String, Object> value, long timeout) {
        // 保存值
        redisTemplate.opsForHash().putAll(key, value);
        // 设置过期时间
        this.expire(key, timeout);

    }

    /**
     * 将哈希表 key 中的域 field 的值设为 value
     *
     * @param key   键
     * @param field 域，一般指对象字段名称
     * @param value 值
     */
    public void hashSet(String key, String field, Object value) {
        redisTemplate.opsForHash().put(key, field, value);
    }

    /**
     * 返回哈希表 key 中给定域 field 的值
     *
     * @param key   键
     * @param field 域，一般指对象字段名称
     * @return
     */
    public Object hashGet(String key, String field) {
        return redisTemplate.opsForHash().get(key, field);
    }

    /**
     * 删除哈希表 key 中的一个或多个指定域，不存在的域将被忽略
     *
     * @param key
     * @param fields
     */
    public void hashDelete(String key, Object... fields) {
        redisTemplate.opsForHash().delete(key, fields);
    }

    /**
     * 返回哈希表 key 中，所有的域和值
     *
     * @param key 键
     * @return 返回 hashKey 对应的所有键值
     */
    public Map<Object, Object> hashGetAll(String key) {
        return redisTemplate.opsForHash().entries(key);
    }

    /**
     * 将一个值 value 插入到列表 key 的表头
     *
     * @param key   键
     * @param value 缓存值
     * @return 返回列表的长度
     */
    public long leftPush(String key, String value) {
        ListOperations<String, Object> operations = redisTemplate.opsForList();
        Long length = operations.leftPush(key, value);

        return length;
    }

    /**
     * 移除并返回列表 key 的头元素。
     *
     * @param key
     * @return 返回列表 key 的头元素。
     */
    public Object leftPop(String key) {
        ListOperations<String, Object> operations = redisTemplate.opsForList();

        return operations.leftPop(key);
    }

    /**
     * 将一个值 value 插入到列表 key 的表尾(最右边)。
     *
     * @param key
     * @param value
     * @return 返回列表的长度。
     */
    public long rightPush(String key, String value) {
        ListOperations<String, Object> operations = redisTemplate.opsForList();
        Long length = operations.rightPush(key, value);

        return length;
    }

}
