package com.csma.redisinaction.ch07.service;

import java.util.Set;

/**
 * 创建索引的相关服务
 * Created by machangsheng on 16/5/26.
 */
public interface IndexService {

    /**
     * 对文章抽取token
     * @param content 文章
     * @return 抽取后的token
     */
    Set<String> tokenize(String content);
    void indexDocument();
}
