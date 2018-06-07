package com.axmor.dao.impl;

import com.axmor.dao.ArticleDao;
import com.axmor.model.Article;
import com.axmor.utils.DataBaseInterface;
import com.axmor.utils.DataBaseUtil;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import static com.axmor.Main.logger;

import static com.axmor.utils.CustomMapper.mapArticleFromResultSet;

public class ArticleDaoImpl implements ArticleDao {

    private DataBaseInterface dataBaseUtil;

    public ArticleDaoImpl(DataBaseInterface dataBaseUtil) {
        this.dataBaseUtil = dataBaseUtil;
    }

    @Override
    public List<Article> findAllArticles() {
        List<Article> articles = new ArrayList<>();
        try (Connection connection = dataBaseUtil.getConnection()) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM ARTICLE");
            while (resultSet.next()) {
                articles.add(mapArticleFromResultSet(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        logger.info("SQL : SELECT * FROM ARTICLE");
        return articles;
    }

    @Override
    public List<Article> findAuthentificatedUserArticles(String username) {
        List<Article> articles = new ArrayList<>();
        try (Connection connection = dataBaseUtil.getConnection()) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM ARTICLE WHERE AUTHOR = '" + username + "'");
            while (resultSet.next()) {
                articles.add(mapArticleFromResultSet(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        logger.info("SQL : SELECT * FROM ARTICLE WHERE AUTHOR = '" + username + "'");
        return articles;
    }

    @Override
    public List<Article> findArticlesByName(String articleName) {
        List<Article> articles = new ArrayList<>();
        try (Connection connection = dataBaseUtil.getConnection()) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM ARTICLE WHERE ARTICLE_NAME LIKE '%" + articleName + "%'");
            while (resultSet.next()) {
                articles.add(mapArticleFromResultSet(resultSet));
            }
            logger.info("SQL : SELECT * FROM ARTICLE WHERE ARTICLE_NAME LIKE '%" + articleName + "%'");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return articles;
    }

    @Override
    public Article findArticleById(Long id) {
        List<Article> articles = new ArrayList<>();
        try (Connection connection = dataBaseUtil.getConnection()) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM ARTICLE WHERE ID = " + id);
            while (resultSet.next()) {
                articles.add(mapArticleFromResultSet(resultSet));
            }
            logger.info("SQL : SELECT * FROM ARTICLE WHERE ID = " + id);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (articles.size() > 0)
            return articles.get(0);
        return null;
    }

    @Override
    public void saveArticle(Article article) {
        try(Connection connection = dataBaseUtil.getConnection()){
            Statement statement = connection.createStatement();
            String values = "'" + article.getArticleName() + "', '" + article.getDescription() +
                    "', 'Created', '" + article.getUsername() + "', " + "NOW()";
            statement.executeUpdate("INSERT INTO ARTICLE(ARTICLE_NAME, DESCRIPTION, STATUS, AUTHOR, DATE) VALUES("+values+")");
            logger.info("SQL : INSERT INTO ARTICLE(ARTICLE_NAME, DESCRIPTION, STATUS, AUTHOR, DATE) VALUES("+values+")");
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
}
