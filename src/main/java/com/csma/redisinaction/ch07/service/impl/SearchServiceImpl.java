package com.csma.redisinaction.ch07.service.impl;

import com.csma.redisinaction.ch07.common.PageableResult;
import com.csma.redisinaction.ch07.entity.Article;
import com.csma.redisinaction.ch07.service.ArticleService;
import com.csma.redisinaction.ch07.service.IndexService;
import com.csma.redisinaction.ch07.service.RedisService;
import com.csma.redisinaction.ch07.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.DefaultSortParameters;
import org.springframework.data.redis.connection.SortParameters;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.query.SortQuery;
import org.springframework.data.redis.core.query.SortQueryBuilder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * 搜索服务
 * Created by csma on 5/27/16.
 */
@Service
public class SearchServiceImpl implements SearchService {

    @Autowired
    private RedisService redisService;
    @Autowired
    private ArticleService articleService;
    /**
     * 根据关键词进行搜索
     *
     * @param keywords 关键词
     * @return
     */
    public PageableResult<Article> searchByKeyWords(String keywords,
        String key, Integer currentPage) {

        if(key != null){
            redisService.getRedisTemplate().expire(key, 30, TimeUnit.SECONDS);
        }else{
            key = parseAndSearch(keywords);
        }

        Long size = redisService.getRedisTemplate().opsForSet().size("idx:" + key);

        if(currentPage == null || currentPage.compareTo(1) < 0){
            currentPage = 1;
        }

        int offset = (currentPage - 1) * PageableResult.PAGE_SIZE;
        SortQuery<String> sortQuery =
                SortQueryBuilder.sort("idx:" + key)//key
                        .by("article:info:*->createdTime")//order by
                        .alphabetical(false)//not alpha
                        .limit(offset, PageableResult.PAGE_SIZE)//start and num
                        .order(SortParameters.Order.DESC)//order
                        .build();
        List<String> articleIds =
                redisService.getRedisTemplate().sort(sortQuery);


        List<Article> articleList = articleService.getArticlesByIds(articleIds);

        PageableResult<Article>
                pageableResult = new PageableResult<Article>(articleList, size);

        pageableResult.setCurrentPage(currentPage);
        pageableResult.setResultKey(key);

        return pageableResult;
    }

    /**
     * 对查询参数进行解析
     * @param keywords
     */
    private SearchWords parse(String keywords){

        Set<String> unwanted = new HashSet<String>();
        Set<String> current = new HashSet<String>();
        List<List<String>> all = new ArrayList<List<String>>();

        String tokens[] = keywords.split(" ");

        for(String token : tokens){

            token = token.toLowerCase();

            char prefix = token.charAt(0);
            if(prefix == '+' || prefix == '-'){
                token  = token.substring(1);
            }else{
                prefix = ' ';
            }

            if(token.length() < 2){
                continue;
            }

            if(prefix == '-'){
                unwanted.add(token);
                continue;
            }

            if(current.size() > 0 && prefix == ' '){
                all.add(new ArrayList<String>(current));
                current = new HashSet<String>();
            }

            current.add(token);
        }

        if(current.size() > 0){
            all.add(new ArrayList<String>(current));
        }

        SearchWords searchWords = new SearchWords();
        searchWords.setQueryWords(all);
        searchWords.setUnwanted(new ArrayList<String>(unwanted));
        return searchWords;
    }

    /**
     * 根据关键词进行查找，并返回搜索得到的ID集合对应的KEY
     * @param queryWords 查询关键词
     * @return 对应的KEY
     */
    private String parseAndSearch(String queryWords){
        SearchWords searchWords = parse(queryWords);

        if(searchWords.getQueryWords().isEmpty()){
            return null;
        }

        List<String> toIntersect = new ArrayList<String>();

        //1. 先收集各个要查询的关键诩，形成一个列表
        //如果每个关键词都只有一个的话，实际上是不需要做这一步的，就是因为可能存在同义诩
        //所以这一步的作用其实是合并同义词
        for(List<String> syn : searchWords.getQueryWords()){
            if(syn.size() > 1){
                //对同义词进行合并
                toIntersect.add(redisService.union(syn));
            }else{
                toIntersect.add(syn.get(0));
            }
        }

        //2. 如果关键词不止一个，则继续取交集
        String intersetResult;
        if(toIntersect.size() > 1){
            intersetResult = redisService.intersect(toIntersect);
        }else{
            intersetResult = toIntersect.get(0);
        }

        //3. 如果有差集的存在，则去掉不搜索的结果
        if(searchWords.getUnwanted().size() > 0){
            searchWords.getUnwanted().add(0, intersetResult);
            return redisService.difference(searchWords.getUnwanted());
        }

        return intersetResult;
    }

}

/**
 * 搜索关键词结构
 */
class SearchWords {

    /**
     * 要搜索的关键记事列表，列表中的元素也是一个列表，代表一组同义诩
     */
    private List<List<String>> queryWords;
    /**
     * 不搜索的关键记事列表
     */
    private List<String> unwanted;

    public List<List<String>> getQueryWords() {
        return queryWords;
    }

    public void setQueryWords(List<List<String>> queryWords) {
        this.queryWords = queryWords;
    }

    public List<String> getUnwanted() {
        return unwanted;
    }

    public void setUnwanted(List<String> unwanted) {
        this.unwanted = unwanted;
    }
}
