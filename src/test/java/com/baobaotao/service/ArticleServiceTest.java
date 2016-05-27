package com.baobaotao.service;

import com.baobaotao.util.Counter;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;
import java.util.Map;

/**
 * Created by machangsheng on 16/5/10.
 */
@ContextConfiguration(
        locations = {"classpath:applicationContext.xml"}
)
@RunWith(SpringJUnit4ClassRunner.class)
public class ArticleServiceTest {

    @Autowired
    private ArticleService articleService;
    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Test
    public void testPostArticle(){
        articleService.postArticle("user:111", "Go to statement considered harmful", "http://goo.gl/kZuSu");
        Map<Object, Object> itemMap = redisTemplate.opsForHash().entries("article:" + Counter.getCurrentId());
        Assert.assertNotNull(itemMap);
        Assert.assertEquals("Go to statement considered harmful", itemMap.get("title"));
        Assert.assertEquals("user:111", itemMap.get("poster"));
        Assert.assertEquals("http://goo.gl/kZuSu", itemMap.get("link"));
        Assert.assertEquals("1", itemMap.get("votes"));
    }

    @Test
    public void testArticleVote(){
        articleService.postArticle("user:111", "Go to statement considered harmful","http://goo.gl/kZuSu");
        int articleId = Counter.getCurrentId();
        articleService.articleVote("user:222", "article:" + articleId);
        articleService.articleVote("user:222", "article:" + articleId);
        articleService.articleVote("user:333", "article:" + articleId);

        Assert.assertEquals(Long.valueOf(3L), redisTemplate.opsForSet().size("voted:" + articleId));
        Assert.assertTrue(redisTemplate.opsForSet().isMember("voted:" + articleId, "user:333"));
        Assert.assertTrue(redisTemplate.opsForSet().isMember("voted:" + articleId, "user:222"));
        Assert.assertTrue(redisTemplate.opsForSet().isMember("voted:" + articleId, "user:111"));
    }

    @Test
    public void testGetArticles(){
        articleService.postArticle("user:111", "Forever Alone:spring break edition","http://goo.gl/kZuSu");
        articleService.postArticle("user:112", "Glowstone driveway","http://goo.gl/kZuSu");
        articleService.postArticle("user:113", "I won the Master Sword and Hylian Shield from the Skyward Swword commericial." +
                "They arrived today","http://goo.gl/kZuSu");
        articleService.postArticle("user:114", "Where is your god now?","http://goo.gl/kZuSu");
        articleService.postArticle("user:115", "I Decided To Consume Only Conservative News Sources For A" +
                " Week, Here's What I Learned", "http://goo.gl/kZuSu");
        articleService.postArticle("user:116", "Unnecessary curly braces in c++?", "http://goo.gl/kZuSu");
        articleService.postArticle("user:117", "Why is 0 + 1 = 2?[closed]", "http://goo.gl/kZuSu");
        articleService.postArticle("user:118", "Different results with Java's digest versus external utilities",
                "http://goo.gl/kZuSu");

        List<Map<Object, Object>> items = articleService.getArticles(1, null);

        Assert.assertNotNull(items);
        Assert.assertEquals(8, items.size());
        Assert.assertEquals("user:118", items.get(0).get("poster"));

    }

    @Test
    public void testAddOrRemoveGroup(){
        String article = "001";
        String toAdd[] = {"Java","Programming","Game"};
        String toRemove[] = {"Game"};

        articleService.addOrRemoveGroups(article, toAdd, toRemove);

        Assert.assertTrue(redisTemplate.opsForSet().isMember("group:Java", "article:" + article));
        Assert.assertTrue(redisTemplate.opsForSet().isMember("group:Programming", "article:" + article));
        Assert.assertFalse(redisTemplate.opsForSet().isMember("group:Game", "article:" + article));
    }

    @Test
    public void testGetArticlesFromGroup(){

        //1. init data
        articleService.postArticle("user:111", "Forever Alone:spring break edition","http://goo.gl/kZuSu");
        articleService.postArticle("user:112", "Glowstone driveway","http://goo.gl/kZuSu");
        articleService.postArticle("user:113", "I won the Master Sword and Hylian Shield from the Skyward Swword commericial." +
                "They arrived today","http://goo.gl/kZuSu");
        articleService.postArticle("user:114", "Where is your god now?","http://goo.gl/kZuSu");
        articleService.postArticle("user:115", "I Decided To Consume Only Conservative News Sources For A" +
                " Week, Here's What I Learned", "http://goo.gl/kZuSu");
        articleService.postArticle("user:116", "Unnecessary curly braces in c++?", "http://goo.gl/kZuSu");
        articleService.postArticle("user:117", "Why is 0 + 1 = 2?[closed]", "http://goo.gl/kZuSu");
        articleService.postArticle("user:118", "Different results with Java's digest versus external utilities",
                "http://goo.gl/kZuSu");

        //2. init group
        String toAdd[] = {"Java","Programming"};

        articleService.addOrRemoveGroups("1", toAdd, null);
        articleService.addOrRemoveGroups("5", toAdd, null);

        List<Map<Object, Object>> items = articleService.getArticlesFromGroup(1, "Java", null);

        Assert.assertNotNull(items);
        Assert.assertEquals(2, items.size());
        Assert.assertEquals("user:115", items.get(0).get("poster"));
    }

}
