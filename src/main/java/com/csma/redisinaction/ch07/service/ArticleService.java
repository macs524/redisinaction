package com.csma.redisinaction.ch07.service;

import com.csma.redisinaction.ch07.entity.Article;

import java.util.List;

/**
 * 文章管理服务
 * Created by machangsheng on 16/5/26.
 */
public interface ArticleService {
    /**
     * 添加文章
     * @param article 文章
     * @return 添加结果
     */
    boolean add(Article article);
    /**
     * 查询默认数量的文章
     * @param index 起始位置
     * @return 文章列表
     */
    List<Article> list(int index);

    /**
     * 根据ID列表查找对应的文章信息
     * @param articleIds ID列表
     * @return 文章信息
     */
    List<Article> getArticlesByIds(List<String> articleIds);

    /**
     * 删除某篇文章
     * @param articleId 文章ID
     * @return 结果
     */
    boolean deleteById(Long articleId);
    Article getById(Long articleId);
}
