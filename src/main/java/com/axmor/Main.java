package com.axmor;

import com.axmor.controller.IssueController;
import com.axmor.controller.CommentController;
import com.axmor.controller.UserController;
import com.axmor.controller.WebController;
import com.axmor.dao.impl.IssueDaoImpl;
import com.axmor.dao.impl.CommentDaoImpl;
import com.axmor.dao.impl.UserDaoImpl;
import com.axmor.exception.IssueNotFoundException;
import com.axmor.exception.LargeValueException;
import com.axmor.exception.UserAlreadyExistException;
import com.axmor.exception.UserNotFoundException;
import com.axmor.service.impl.IssueServiceImpl;
import com.axmor.service.impl.UserServiceImpl;
import com.axmor.utils.*;
import spark.Spark;

import java.util.logging.Logger;

import static spark.Spark.*;

/**
 * Application entry point
 */
public class Main {

    public static final ThymeleafTemplateEngine engine = new ThymeleafTemplateEngine();
    public static UserController userController;
    public static IssueController controller;
    public static CommentController commentController;
    public static WebController webController;

    public static void main(String[] args) {
        String queryPath = "src\\main\\resources\\public\\sql\\create_tables";

        DataSource.createAllTables(queryPath);

        controller = new IssueController(new IssueServiceImpl(new IssueDaoImpl()));
        commentController = new CommentController(new CommentDaoImpl());
        userController = new UserController(new UserServiceImpl(new UserDaoImpl()));
        webController = new WebController();

        port(80);

        staticFileLocation("/public");
        notFound((req, res) ->
        {
            res.type("application/json");
            return "{\"message\":\"Resource Not Found\"}";
        });

        Spark.before(Path.Web.ISSUES + "/*",     userController::checkUser);
        Spark.before(Path.Web.ISSUES,                 userController::checkUser);

        Spark.get(Path.Web.ISSUES,                    controller.getAllIssues);
        Spark.get(Path.Web.ONE_ISSUE,                 controller.getIssue);
        Spark.get(Path.Web.COMMENTS,                  commentController.getAllCommentsByIssue);
        Spark.get(Path.Web.LOGOUT,                    userController.logout);
        Spark.get(Path.Web.LOGIN,                     userController.loginPage);
        Spark.get(Path.Web.REGISTRATION,              userController.registrationPage);
        Spark.get(Path.Web.NEW_ISSUE,                 controller.newIssuePage);
        Spark.get(Path.Web.INDEX,                     webController.welcome);

        Spark.post(Path.Web.REGISTRATION_SAVE,        userController.saveUser);
        Spark.post(Path.Web.LOGIN,                    userController.userAuth);
        Spark.post(Path.Web.COMMENTS,                 commentController.saveComment);
        Spark.post(Path.Web.NEW_ISSUE,                controller::createIssue);

        Spark.exception(UserNotFoundException.class, (exception, request, response) -> {
            response.status(400);
            response.body(exception.getMessage());
        });
        Spark.exception(UserAlreadyExistException.class, (exception, request, response) -> {
            response.status(400);
            response.body(exception.getMessage());
        });
        Spark.exception(IssueNotFoundException.class, (exception, request, response) -> {
            response.status(400);
            response.body(exception.getMessage());

        });Spark.exception(LargeValueException.class, (exception, request, response) -> {
            response.status(400);
            response.body(exception.getMessage());
        });
    }
}
