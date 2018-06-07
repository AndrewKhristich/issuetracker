package com.axmor.dao.impl;

import com.axmor.dao.CommentDao;
import com.axmor.model.Comment;
import com.axmor.utils.DataBaseInterface;
import com.axmor.utils.DataBaseUtil;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import static com.axmor.Main.logger;


import static com.axmor.utils.CustomMapper.mapCommentFromResultSet;

public class CommentDaoImpl implements CommentDao {

    private DataBaseInterface dataBaseUtil;

    public CommentDaoImpl(DataBaseInterface dataBaseUtil) {
        this.dataBaseUtil = dataBaseUtil;
    }

    @Override
    public List<Comment> findAllCommentsByArticleId(Long id) {
        List<Comment> comments = new ArrayList<>();
        try(Connection connection = dataBaseUtil.getConnection()) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM COMMENT WHERE ARTICLE_ID = " + id + " ORDER BY PUBLISHED_DATE");
            while (resultSet.next()){
                comments.add(mapCommentFromResultSet(resultSet));
            }
            logger.info("SQL : SELECT * FROM COMMENT WHERE ARTICLE_ID = " + id + " ORDER BY PUBLISHED_DATE");
        } catch (SQLException e){
            e.printStackTrace();
        }
        return comments;
    }

    @Override
    public void saveComment(Comment comment) {
        try(Connection connection = dataBaseUtil.getConnection()){
            Statement statement = connection.createStatement();
            String status = comment.getStatus();
            if (!status.equals("Resolved")) {
                status = null;
            } else {
                statement.executeUpdate("UPDATE ARTICLE SET STATUS='Resolved' WHERE ID="+comment.getArticleId());
                logger.info("SQL : UPDATE ARTICLE SET STATUS='Resolved' WHERE ID="+comment.getArticleId());
            }
            String commentValue = ("'"+comment.getUserName()+"', '"
                    +comment.getArticleId()+"', '"
                    +comment.getCommentDescription()+"', "+
                    "NOW(), '" + status + "'");
            statement.executeUpdate("INSERT INTO COMMENT(USERNAME, ARTICLE_ID, COMMENT_DESCRIPTION, PUBLISHED_DATE, STATUS) VALUES("+commentValue+")");
            logger.info("SQL : INSERT INTO COMMENT(USERNAME, ARTICLE_ID, COMMENT_DESCRIPTION, PUBLISHED_DATE, STATUS) VALUES("+commentValue+")");
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
}
