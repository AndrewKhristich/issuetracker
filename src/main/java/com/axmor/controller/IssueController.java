package com.axmor.controller;

import com.axmor.Main;
import com.axmor.model.Issue;
import com.axmor.service.IssueService;
import com.axmor.utils.Path;
import com.google.gson.Gson;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.Route;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class IssueController {

    private IssueService issueService;

    public IssueController(IssueService issueService) {
        this.issueService = issueService;
    }

    public String createIssue(Request request, Response response) {
        Gson gson = new Gson();
        Issue issue = gson.fromJson(request.body(), Issue.class);
        issueService.saveIssue(issue);
        return "ok";
    }

    public Route newIssuePage = (Request request, Response response) -> {
        Map<String, Object> map = new HashMap<>();
        String logged = request.session().attribute("logged");
        map.put("user", logged);
        return Main.engine.render(new ModelAndView(map, Path.Templates.NEW_ISSUE_PAGE));
    };

    public Route getAllIssues = (request, response) -> {
        Map<String, Object> map = new HashMap<>();
        List<Issue> allIssues;
        String search = request.queryParams("search");
        String my = request.queryParams("my");
        boolean searchCondition = search != null;
        boolean myParamCondition = my != null;
        if (searchCondition) allIssues = issueService.findAllIssuesByName(search);
        else if (myParamCondition) allIssues = issueService
                .findAuthentificatedUserIssue(request.session().attribute("logged"));
        else allIssues = issueService.findAllIssues();
        map.put("issuesList", allIssues);

        return Main.engine.render(new ModelAndView(map, Path.Templates.ISSUE_PAGE));
    };

    public Route getIssue = (request, response) -> {
        Long id = Long.valueOf(request.queryParams("id"));
        Map<String, Object> map = new HashMap<>();
        Issue issueById = issueService.findIssuesById(id);
        map.put("issue", issueById);
        String login = request.session().attribute("logged");
        map.put("user", login);
        return Main.engine.render(new ModelAndView(map, Path.Templates.SINGLE_ISSUE_PAGE));
    };

}
