package com.csma.redisinaction.ch07.service;

import com.csma.redisinaction.ch07.common.PageableResult;
import com.csma.redisinaction.ch07.entity.Article;

/**
 * 搜索服务
 * Created by csma on 5/27/16.
 */
public interface SearchService {

    /**
     * 根据关键词进行搜索
     * @param keywords 关键词
     * @return
     */
    PageableResult<Article> searchByKeyWords(String keywords,String key, Integer currentPage);
}
