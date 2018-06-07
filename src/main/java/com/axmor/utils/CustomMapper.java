package com.axmor.utils;

import com.axmor.model.Article;
import com.axmor.model.Comment;
import com.axmor.model.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

public class CustomMapper {

    public static Article mapArticleFromResultSet(ResultSet set) throws SQLException {
        Article article = new Article();
        article.setId(set.getLong("id"));
        article.setStatus(set.getString("status"));
        article.setArticleName(set.getString("article_name"));
        article.setDescription(set.getString("description"));
        article.setUsername(set.getString("author"));
        article.setDate(set.getDate("date"));
        return article;
    }

    public static User mapUserFromResultSet(ResultSet set) throws SQLException {
        User user = new User();
        user.setName(set.getString("name"));
        user.setPassword(set.getString("password"));
        return user;
    }

    public static Comment mapCommentFromResultSet(ResultSet set) throws SQLException {
        Comment comment = new Comment();
        comment.setId(set.getLong("id"));
        comment.setArticleId(set.getLong("article_id"));
        comment.setUserName(set.getString("username"));
        comment.setCommentDescription(set.getString("comment_description"));
        comment.setDate(set.getTimestamp("PUBLISHED_DATE"));
        return comment;
    }

}
