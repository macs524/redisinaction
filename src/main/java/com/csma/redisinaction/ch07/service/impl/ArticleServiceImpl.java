package com.csma.redisinaction.ch07.service.impl;

import com.csma.redisinaction.ch07.entity.Article;
import com.csma.redisinaction.ch07.service.ArticleService;
import com.csma.redisinaction.ch07.service.IndexService;
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
    @Autowired
    private IndexService indexService;

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
            long createdTime = System.currentTimeMillis();
            article.setCreatedTime(new Date(createdTime));

            Map<String, String> articleMap =
                    new HashMap<String, String>();

            articleMap.put("author", article.getAuthor());
            articleMap.put("content", article.getContent());
            articleMap.put("createdTime", createdTime + "");
            articleMap.put("title", article.getTitle());


            redisTemplate.opsForHash().putAll("article:info:" + lngId,
                    articleMap);

            //存到有序列表里
            redisTemplate.opsForZSet().add("article:list",
                    lngId + "", createdTime);

            //建索引
            indexService.indexDocument(article);

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

        return getArticlesByIds(new ArrayList<String>(articleIds));
    }

    public List<Article> getArticlesByIds(List<String> articleIds){

        List<Article> articleList = new ArrayList<Article>();

        if(articleIds != null){
            for(String articleId : articleIds){

                Article article = getById(Long.valueOf(articleId));
                if(article != null){
                    if(article.getContent().length() > 20){
                        article.setContent(article.getContent().substring(0, 20) + "...");
                    }
                    articleList.add(article);
                }
            }
        }

        return articleList;
    }

    /**
     * 删除某篇文章
     *
     * @param articleId 文章ID
     * @return 结果
     */
    public boolean deleteById(Long articleId) {

        String articleKey = "article:info:" + articleId;
        if(redisTemplate.hasKey(articleKey)){
            redisTemplate.delete(articleKey);
            redisTemplate.opsForZSet().remove("article:list", articleId + "");

            return true;
        }
        return false;
    }

    public Article getById(Long articleId) {
        Map<Object, Object> articleMap
                = redisTemplate.opsForHash().entries("article:info:" + articleId);

        Article article = null;
        if(!articleMap.isEmpty()){
            article = new Article();
            article.setId(articleId);
            article.setAuthor(articleMap.get("author").toString());

            String content = articleMap.get("content").toString();
            article.setContent(content);
            article.setTitle(articleMap.get("title").toString());
            article.setCreatedTime(new Date(Long.valueOf(articleMap.get("createdTime").toString())));
        }

        return article;
    }

}
