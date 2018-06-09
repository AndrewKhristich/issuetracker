package com.axmor.controller;

import com.axmor.Main;
import com.axmor.dao.CommentDao;
import com.axmor.model.Comment;
import com.axmor.utils.Path;
import com.google.gson.Gson;
import spark.ModelAndView;
import spark.Route;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    public Route getAllCommentsByIssue = (request, response) -> {
        Map<String, Object> map = new HashMap<>();
        Long id = Long.valueOf(request.queryParams("issue"));
        List<Comment> allCommentsByIssueId = commentDao.findAllCommentsByIssueId(id);
        map.put("comments", allCommentsByIssueId);
        map.put("art_id", id);
        return Main.engine.render(new ModelAndView(map, Path.Templates.ISSUE_COMMENTS_PAGE));
    };
}
