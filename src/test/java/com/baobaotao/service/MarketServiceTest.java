package com.baobaotao.service;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Arrays;
import java.util.List;

/**
 * Created by machangsheng on 16/5/16.
 */
@ContextConfiguration(
        locations = {"classpath:applicationContext.xml"}
)
@RunWith(SpringJUnit4ClassRunner.class)
public class MarketServiceTest {

    @Autowired
    private MarketService marketService;
    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Test
    public void testAddUserInfo(){

        List<String> bags17 = Arrays.asList("ItemL", "ItemM", "ItemN");
        marketService.addUserInfo(17L, "Frank", 43.0, bags17);

        List<String> bags27 = Arrays.asList("ItemO", "ItemP", "ItemQ");
        marketService.addUserInfo(27L, "Bill", 125.0, bags27);

        Assert.assertEquals(3L, redisTemplate.opsForSet().size("inventory:17").longValue());
        Assert.assertEquals(3L, redisTemplate.opsForSet().size("inventory:27").longValue());
    }

    @Test
    public void testPutToMarket(){
        testAddUserInfo();
        marketService.putToMarket("ItemM", 17L, 97.0);

        Assert.assertEquals(2L, redisTemplate.opsForSet().size("inventory:17").longValue());
        Assert.assertEquals(1L, redisTemplate.opsForZSet().size("market:").longValue());

        Assert.assertEquals(Double.valueOf(97), redisTemplate.opsForZSet().score("market:",
                "ItemM.17"));
    }

    @Test
    public void testBuyPurchase(){
        testPutToMarket();
        marketService.buyPurchase(27L, "ItemM", 17L, 97.0);
        Assert.assertEquals(0L, redisTemplate.opsForZSet().size("market:").longValue());
        Assert.assertEquals("140", redisTemplate.opsForHash().get("users:17", "funds"));
        Assert.assertEquals("28",  redisTemplate.opsForHash().get("users:27", "funds"));
        Assert.assertEquals(4L, redisTemplate.opsForSet().size("inventory:27").longValue());
    }


    @Before
    public void init(){
        redisTemplate.delete("users:17");
        redisTemplate.delete("users:27");
        redisTemplate.delete("inventory:17");
        redisTemplate.delete("inventory:27");
        redisTemplate.delete("market:");
    }
}
