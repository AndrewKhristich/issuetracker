package com.axmor.service;

import com.axmor.model.Article;

import java.util.List;

public interface ArticleService {
    List<Article> findAllArticles();
    List<Article> findAllArticlesByName(String name);
    Article findArticleById(Long id);
    void saveArticle(Article article);
    List<Article> findAuthentificatedUserArticles(String username);

}
