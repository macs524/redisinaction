package com.csma.redisinaction.ch07.controller;

import com.csma.redisinaction.ch07.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 搜索文章
 * Created by csma on 5/30/16.
 */
@RequestMapping("/article")
@Controller
public class SearchController {

    @Autowired
    private SearchService searchService;

    @RequestMapping("/searchArticle")
    private String searchByParam(String keywords,
                                 String key, Integer currentPage,
        Model model){

        model.addAttribute("pageableResult",
                searchService.searchByKeyWords(keywords,
                        key, currentPage));
        model.addAttribute("keywords", keywords);

        return "searchResult";
    }
}
