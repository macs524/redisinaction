package com.csma.redisinaction.ch07.service.impl;

import com.csma.redisinaction.ch07.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * REDIS公共操作
 * Created by csma on 5/27/16.
 */
@Service
public class RedisServiceImpl implements RedisService {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    public String intersect(List<String> items) {
        return operationSet(1, items);
    }

    /**
     * 取并集
     *
     * @param items
     * @return
     */
    public String union(List<String> items) {
        return operationSet(2, items);
    }

    /**
     * 取差集
     *
     * @param items
     * @return
     */
    public String difference(List<String> items) {
        return operationSet(3, items);
    }

    /**
     * 提供对TEMPLATE的直接访问以更方便的操作
     *
     * @return
     */
    public RedisTemplate<String, String> getRedisTemplate() {
        return redisTemplate;
    }

    private String operationSet(int type, List<String> items){

        String destKey = "idx:" + UUID.randomUUID().toString();//.replaceAll("-","")
        //本方法的作用是将
        List<String> keysList = new ArrayList<String>();
        for(String item : items){
            keysList.add("idx:" + item);
        }
        if(type == 1) {
            redisTemplate.opsForSet().intersectAndStore(null, keysList, destKey);
        }else if(type == 2){
            redisTemplate.opsForSet().unionAndStore(null, keysList, destKey);
        }else{
            redisTemplate.opsForSet().differenceAndStore(null, keysList, destKey);
        }

        //30秒过期
        redisTemplate.expire(destKey, 30L, TimeUnit.SECONDS);

        return destKey;
    }


}
