package com.axmor.service.impl;

import com.axmor.dao.ArticleDao;
import com.axmor.exception.ArticleNotFoundException;
import com.axmor.model.Article;
import com.axmor.service.ArticleService;

import java.util.List;

public class ArticleServiceImpl implements ArticleService {

    private ArticleDao articleDao;

    public ArticleServiceImpl(ArticleDao articleDao) {
        this.articleDao = articleDao;
    }

    @Override
    public List<Article> findAllArticles() {
        return articleDao.findAllArticles();
    }

    @Override
    public List<Article> findAllArticlesByName(String name) {
        return articleDao.findArticlesByName(name);
    }

    @Override
    public Article findArticleById(Long id) {
        Article articleById = articleDao.findArticleById(id);
        if (articleById==null){
            throw new ArticleNotFoundException("Article does not exist");
        }
        return articleById;
    }

    @Override
    public void saveArticle(Article article) {
        articleDao.saveArticle(article);
    }

    @Override
    public List<Article> findAuthentificatedUserArticles(String username) {
        return articleDao.findAuthentificatedUserArticles(username);
    }
}
