package com.axmor.controller;

import com.axmor.Main;
import com.axmor.utils.Path;
import spark.ModelAndView;
import spark.Route;

import java.util.HashMap;

public class WebController {

    public Route welcome = (request, response) -> Main.engine.render(new ModelAndView(new HashMap<>(), Path.Templates.INDEX_PAGE));

}
