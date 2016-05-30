package com.csma.redisinaction.ch07.service;

import org.springframework.data.redis.core.RedisTemplate;

import java.util.List;

/**
 * Created by csma on 5/27/16.
 */
public interface RedisService {
    /**
     * 取交集
     * @param items
     * @return
     */
    String intersect(List<String> items);

    /**
     * 取并集
     * @param items
     * @return
     */
    String union(List<String> items);

    /**
     * 取差集
     * @param items
     * @return
     */
    String difference(List<String> items);

    /**
     * 提供对TEMPLATE的直接访问以更方便的操作
     * @return
     */
    RedisTemplate<String, String> getRedisTemplate();
}
