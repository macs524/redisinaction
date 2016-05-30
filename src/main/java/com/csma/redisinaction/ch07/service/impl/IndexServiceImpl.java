package com.csma.redisinaction.ch07.service.impl;

import com.csma.redisinaction.ch07.entity.Article;
import com.csma.redisinaction.ch07.service.IndexService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Pattern;

/**
 * 索引服务类 Created by csma on 5/27/16.
 */
@Service
public class IndexServiceImpl implements IndexService {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    /**
     * 对文章抽取token
     *
     * @param content 文章
     * @return 抽取后的token
     */
    public Set<String> tokenize(String content) {

        if (content != null) {
            // 暂时做简单的处理
            String words[] = content.trim().toLowerCase().split(" ");
            Set<String> tokens = new HashSet<String>();

            Pattern PE = Pattern.compile("[a-z]{2,}");
            for (String word : words) {
                if (PE.matcher(word).matches()) {
                    tokens.add(word);
                }
            }

            if (!tokens.isEmpty()) {
                // 移除STOP WORDS
                tokens.removeAll(STOP_SET);
            }

            return tokens;
        }
        return Collections.emptySet();
    }

    public void indexDocument(Article article) {

        Set<String> tokens = tokenize(article.getContent());

        for (String token : tokens) {
            redisTemplate.opsForSet().add("idx:" + token, article.getId() + "");
        }

    }

    private final static String STOP_WORDS[] = {"'tis", "'twas", "a", "able", "about", "across",
            "after", "ain't", "all", "almost", "also", "am", "among", "an", "and", "any", "are",
            "aren't", "as", "at", "be", "because", "been", "but", "by", "can", "can't", "cannot",
            "could", "could've", "couldn't", "dear", "did", "didn't", "do", "does", "doesn't",
            "don't", "either", "else", "ever", "every", "for", "from", "get", "got", "had", "has",
            "hasn't", "have", "he", "he'd", "he'll", "he's", "her", "hers", "him", "his", "how",
            "how'd", "how'll", "how's", "however", "i", "i'd", "i'll", "i'm", "i've", "if", "in",
            "into", "is", "isn't", "it", "it's", "its", "just", "least", "let", "like", "likely",
            "may", "me", "might", "might've", "mightn't", "most", "must", "must've", "mustn't",
            "my", "neither", "no", "nor", "not", "of", "off", "often", "on", "only", "or", "other",
            "our", "own", "rather", "said", "say", "says", "shan't", "she", "she'd", "she'll",
            "she's", "should", "should've", "shouldn't", "since", "so", "some", "than", "that",
            "that'll", "that's", "the", "their", "them", "then", "there", "there's", "these",
            "they", "they'd", "they'll", "they're", "they've", "this", "tis", "to", "too", "twas",
            "us", "wants", "was", "wasn't", "we", "we'd", "we'll", "we're", "were", "weren't",
            "what", "what'd", "what's", "when", "when", "when'd", "when'll", "when's", "where",
            "where'd", "where'll", "where's", "which", "while", "who", "who'd", "who'll", "who's",
            "whom", "why", "why'd", "why'll", "why's", "will", "with", "won't", "would", "would've",
            "wouldn't", "yet", "you", "you'd", "you'll", "you're", "you've", "your"};

    private static Set<String> STOP_SET = new HashSet<String>();

    static {
        for (String stopWord : STOP_WORDS) {
            STOP_SET.add(stopWord);
        }
    }

}
