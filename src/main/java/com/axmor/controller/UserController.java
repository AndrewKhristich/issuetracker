package com.axmor.controller;

import com.axmor.Main;
import com.axmor.exception.UserNotFoundException;
import com.axmor.model.User;
import com.axmor.service.UserService;
import com.axmor.utils.Path;
import com.google.gson.Gson;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.Route;

import java.util.HashMap;

public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    public void checkUser(Request request, Response response) {
        Object logged = request.session().attribute("logged");
        if ((logged == null)) {
            response.redirect(Path.Web.LOGIN);
        }
    }

    public Route loginPage = (request, response) -> {
        String logged = request.session().attribute("logged");
        if (logged==null)
        return Main.engine.render(new ModelAndView(new HashMap<>(), Path.Templates.LOGIN_PAGE));
        else {
            response.redirect(Path.Web.ISSUES);
            return null;
        }
    };

    public Route logout = (request, response) -> {
        request.session().removeAttribute("logged");
        response.redirect(Path.Web.LOGIN);
        return null;
    };

    public Route registrationPage = ((request, response) ->
            Main.engine.render(new ModelAndView(new HashMap<>(), Path.Templates.REGISTRATION))
    );

    public Route saveUser = (request, response) -> {
        User user = new Gson().fromJson(request.body(), User.class);
        userService.saveUser(user.getName(), user.getPassword());
        return new Gson().toJson("Success");
    };

    public Route userAuth = ((request, response) -> {
        User user = new Gson().fromJson(request.body(), User.class);
        User userByName = userService.findUser(user.getName());
        if (user.equals(userByName)) {
            request.session().attribute("logged", user.getName());
            response.redirect(Path.Web.ISSUES);
            return new Gson().toJson("Logged in");
        } else {
            throw new UserNotFoundException("Wrong user name or password");
        }
    });

}
