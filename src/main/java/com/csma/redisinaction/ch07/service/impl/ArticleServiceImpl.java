package com.csma.redisinaction.ch07.service.impl;

import com.csma.redisinaction.ch07.entity.Article;
import com.csma.redisinaction.ch07.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * 文章处理类
 * Created by machangsheng on 16/5/26.
 */
@Service
public class ArticleServiceImpl implements ArticleService {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    /**
     * 添加文章
     *
     * @param article 文章
     * @return 添加结果
     */
    public boolean add(Article article) {

        if(article != null && article.getAuthor() != null
                && article.getContent() != null){

            //添加文章, 首先找到最新的ID
            redisTemplate.opsForValue().increment("article:id", 1);
            Long lngId = Long.valueOf(redisTemplate.opsForValue().get("article:id"));

            article.setId(lngId);
            article.setCreatedTime(System.currentTimeMillis());

            Map<String, String> articleMap =
                    new HashMap<String, String>();

            articleMap.put("author", article.getAuthor());
            articleMap.put("content", article.getContent());
            articleMap.put("createdTime", article.getCreatedTime() + "");
            articleMap.put("title", article.getTitle());


            redisTemplate.opsForHash().putAll("article:info:" + lngId,
                    articleMap);

            //存到有序列表里
            redisTemplate.opsForZSet().add("article:list",
                    lngId + "", article.getCreatedTime());

            return true;
        }
        return false;
    }

    /**
     * 查询默认数量的文章
     *
     * @param index 起始位置
     * @return 文章列表
     */
    public List<Article> list(int index) {

        Set<String> articleIds =
                redisTemplate.opsForZSet().reverseRange("article:list",
                index + 1, index + 20);

        List<Article> articleList = new ArrayList<Article>();

        if(articleIds != null){
            for(String articleId : articleIds){
                Map<Object, Object> articleMap
                        = redisTemplate.opsForHash().entries("article:info:" + articleId);

                if(articleMap != null){
                    Article article = new Article();
                    article.setId(Long.valueOf(articleId));
                    article.setAuthor(articleMap.get("author").toString());
                    article.setContent(articleMap.get("content").toString());
                    article.setTitle(articleMap.get("title").toString());
                    article.setCreatedTime(Long.valueOf(articleMap.get("createdTime").toString()));

                    articleList.add(article);

                }
            }
        }
        return articleList;
    }
}
