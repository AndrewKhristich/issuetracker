package com.axmor;

import com.axmor.controller.ArticleController;
import com.axmor.controller.CommentController;
import com.axmor.controller.UserController;
import com.axmor.controller.WebController;
import com.axmor.dao.impl.ArticleDaoImpl;
import com.axmor.dao.impl.CommentDaoImpl;
import com.axmor.dao.impl.UserDaoImpl;
import com.axmor.exception.ArticleNotFoundException;
import com.axmor.exception.UserAlreadyExistException;
import com.axmor.exception.UserNotFoundException;
import com.axmor.service.impl.ArticleServiceImpl;
import com.axmor.service.impl.UserServiceImpl;
import com.axmor.utils.DataBaseUtil;
import com.axmor.utils.Path;
import com.axmor.utils.ThymeleafTemplateEngine;
import spark.Spark;

import java.util.logging.Logger;

import static spark.Spark.*;

/**
 * Application entry point
 */
public class Main {
    public static final DataBaseUtil dataBaseUtil = new DataBaseUtil();
    public static final ThymeleafTemplateEngine engine = new ThymeleafTemplateEngine();
    public static final Logger logger = Logger.getGlobal();
    public static UserController userController;
    public static ArticleController controller;
    public static CommentController commentController;
    public static WebController webController;

    public static void main(String[] args) {
        String queryPath = "src\\main\\resources\\public\\sql\\create_tables";

        dataBaseUtil.createAllTables(queryPath);

        controller = new ArticleController(new ArticleServiceImpl(new ArticleDaoImpl(dataBaseUtil)));
        commentController = new CommentController(new CommentDaoImpl(dataBaseUtil));
        userController = new UserController(new UserServiceImpl(new UserDaoImpl(dataBaseUtil)));
        webController = new WebController();

        port(80);

        staticFileLocation("/public");
        notFound((req, res) ->
        {
            res.type("application/json");
            return "{\"message\":\"Resource Not Found\"}";
        });

        Spark.before(Path.Web.ARTICLES + "/*",     userController::checkUser);
        Spark.before(Path.Web.ARTICLES,                 userController::checkUser);

        Spark.get(Path.Web.ARTICLES,                    controller.getAllArticles);
        Spark.get(Path.Web.ONE_ARTICLE,                 controller.getArticle);
        Spark.get(Path.Web.COMMENTS,                    commentController.getAllCommentsByArticle);
        Spark.get(Path.Web.LOGOUT,                      userController.logout);
        Spark.get(Path.Web.LOGIN,                       userController.loginPage);
        Spark.get(Path.Web.REGISTRATION,                userController.registrationPage);
        Spark.get(Path.Web.NEW_ARTICLE,                 controller.newArticlePage);
        Spark.get(Path.Web.INDEX,                       webController.welcome);

        Spark.post(Path.Web.REGISTRATION_SAVE,          userController.saveUser);
        Spark.post(Path.Web.LOGIN,                      userController.userAuth);
        Spark.post(Path.Web.COMMENTS,                   commentController.saveComment);
        Spark.post(Path.Web.NEW_ARTICLE,                controller::createArticle);

        Spark.exception(UserNotFoundException.class, (exception, request, response) -> {
            response.status(400);
            response.body(exception.getMessage());
        });
        Spark.exception(UserAlreadyExistException.class, (exception, request, response) -> {
            response.status(400);
            response.body(exception.getMessage());
        });
        Spark.exception(ArticleNotFoundException.class, (exception, request, response) -> {
            response.status(400);
            response.body(exception.getMessage());
        });
    }
}
