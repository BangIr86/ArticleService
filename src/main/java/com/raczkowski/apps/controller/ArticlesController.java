package com.raczkowski.apps.controller;

import com.raczkowski.apps.model.Article;
import com.raczkowski.apps.model.Comment;
import com.raczkowski.apps.service.ArticleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;


import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping(value = "/articles")
public class ArticlesController extends HttpServlet {
    private final ArticleService articleService;
    private final Logger logger = LoggerFactory.getLogger(ArticlesController.class);

    public ArticlesController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @GetMapping(value = "/all", produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public List<Article> getAllArticles() {
        return articleService.getAllArticles();
    }

    @GetMapping(value = "/{id}", produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public void getAllArticlesById(@PathVariable String id, HttpServletRequest request, HttpServletResponse response) throws IOException {
        logger.info("Got request parameters: " + request.getParameterMap());
        response.getWriter().write(String.valueOf(articleService.getArticlesById(id)));
    }

    @RequestMapping(value = "/addArticle/{title}&{content}", method = RequestMethod.POST, produces = {MediaType.APPLICATION_JSON_VALUE})
    public void addNewArticle(@PathVariable("title") String title, @PathVariable("content") String content, HttpServletResponse response) throws IOException {
        articleService.addArticle(title, content);
        response.getWriter().write("Saved");
    }

    @GetMapping(value = "/comments/{idOfArticle}")
    @ResponseBody
    public List<Comment> getCommentsOfArticle(@PathVariable int idOfArticle) {
        return articleService.getCommentsOfArticle(idOfArticle);
    }

    @GetMapping(value = "/newest")
    @ResponseBody
    public List<Article> getNewestArticle() {
        return articleService.getNewestArticle();
    }

    @GetMapping(value = "/{author}")
    @ResponseBody
    public List<Article> getNewestArticle(@PathVariable String author) {
        return articleService.getArticleOfAuthor(author);
    }

    @GetMapping(value = "/sort/{choice}")
    @ResponseBody
    public List<Article> getSortedArticles(@PathVariable String choice, HttpServletResponse response) {
        return articleService.sortArticles(choice);
    }
}