package com.axmor.dao;

import com.axmor.model.Article;

import java.util.List;

public interface ArticleDao {

    List<Article> findAllArticles();
    List<Article> findAuthentificatedUserArticles(String username);
    List<Article> findArticlesByName(String articleName);
    Article findArticleById(Long id);
    void saveArticle(Article article);
}
