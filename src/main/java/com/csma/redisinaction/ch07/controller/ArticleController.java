package com.csma.redisinaction.ch07.controller;

//import com.baobaotao.service.ArticleService;

import com.csma.redisinaction.ch07.entity.Article;
import com.csma.redisinaction.ch07.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 一个简单的文章管理系统
 * Created by machangsheng on 16/5/10.
 */
@RequestMapping("/article")
@Controller
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    @RequestMapping("list")
    public String getArticles(HttpServletRequest request,
        Model model){

        String pageIndex = request.getParameter("pageIndex");

        if(pageIndex == null){
            pageIndex = "-1";
        }

        List<Article> articlesList
                = articleService.list(Integer.valueOf(pageIndex));

        model.addAttribute("list", articlesList);

        return "articles";
    }

    @RequestMapping(value = "addNew", method = RequestMethod.GET)
    public String addNew(){
        return "addNew";
    }

    /**
     * 添加文章
     * @param author author
     * @param content content
     * @return result
     */
    @RequestMapping(value="addNew", method = RequestMethod.POST)
    public String doAddNew(String author, String content){
        if(author == null || content == null){
            return "redirect:addNew";
        }

        Article article = new Article();
        article.setContent(content);
        article.setAuthor(author);
        articleService.add(article);

        return "redirect:list";
    }
}
