package com.axmor.controller;

import com.axmor.Main;
import com.axmor.dao.CommentDao;
import com.axmor.model.Comment;
import com.axmor.utils.Path;
import com.axmor.utils.ThymeleafTemplateEngine;
import com.google.gson.Gson;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.Route;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

public class CommentController {

    private CommentDao commentDao;

    public CommentController(CommentDao commentDao) {
        this.commentDao = commentDao;
    }

    public Route saveComment = (request, response) -> {
        Main.userController.checkUser(request, response);
        Gson gson = new Gson();
        Comment comment = gson.fromJson(request.body(), Comment.class);
        commentDao.saveComment(comment);
        return "ok";
    };

    public Route getAllCommentsByArticle = (request, response) -> {
        Map<String, Object> map = new HashMap<>();
        Long id = Long.valueOf(request.queryParams("article"));
        List<Comment> allCommentsByArticleId = commentDao.findAllCommentsByArticleId(id);
        map.put("comments", allCommentsByArticleId);
        map.put("art_id", id);
        return Main.engine.render(new ModelAndView(map, Path.Templates.ARTICLE_COMMENTS_PAGE));
    };
}
