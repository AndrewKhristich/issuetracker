package com.axmor.controller;

import com.axmor.Main;
import com.axmor.utils.Path;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.Route;

import java.util.HashMap;

public class WebController {

    public void welcome(Request request, Response response) {
        response.redirect(Path.Web.LOGIN);
    }

}
