package com.axmor.controller;

import com.axmor.Main;
import com.axmor.model.Article;
import com.axmor.service.ArticleService;
import com.axmor.utils.Path;
import com.google.gson.Gson;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.Route;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ArticleController {

    private ArticleService articleService;

    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    public String createArticle(Request request, Response response) {
        Gson gson = new Gson();
        Article article = gson.fromJson(request.body(), Article.class);
        articleService.saveArticle(article);
        return "ok";
    }

    public Route newArticlePage = (Request request, Response response) -> {
        Map<String, Object> map = new HashMap<>();
        String logged = request.session().attribute("logged");
        map.put("user", logged);
        return Main.engine.render(new ModelAndView(map, Path.Templates.NEW_ARTICLE_PAGE));
    };

    public Route getAllArticles = (request, response) -> {
        Map<String, Object> map = new HashMap<>();
        List<Article> allArticles;
        String search = request.queryParams("search");
        String my = request.queryParams("my");
        boolean searchCondition = search != null;
        boolean myParamCondition = my != null;
        if (searchCondition) allArticles = articleService.findAllArticlesByName(search);
        else if (myParamCondition) allArticles = articleService
                .findAuthentificatedUserArticles(request.session().attribute("logged"));
        else allArticles = articleService.findAllArticles();
        map.put("articleList", allArticles);

        return Main.engine.render(new ModelAndView(map, Path.Templates.ARTICLE_PAGE));
    };

    public Route getArticle = (request, response) -> {
        Long id = Long.valueOf(request.queryParams("id"));
        Map<String, Object> map = new HashMap<>();
        Article articleById = articleService.findArticleById(id);
        map.put("article", articleById);
        String login = request.session().attribute("logged");
        map.put("user", login);
        System.out.println(login + "===============================");
        return Main.engine.render(new ModelAndView(map, Path.Templates.SINGLE_ARTICLE_PAGE));
    };

}
