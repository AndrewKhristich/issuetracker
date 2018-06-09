package com.axmor.utils;

public class Path {

   public static class Web {
       public static final String INDEX = "/";
       public static final String ISSUES = "/issues";
       public static final String ONE_ISSUE = "/issues/art";
       public static final String COMMENTS = "/issues/comment";
       public static final String LOGIN = "/login";
       public static final String LOGOUT = "/logout";
       public static final String REGISTRATION = "/registration";
       public static final String REGISTRATION_SAVE = "/registration/save";
       public static final String NEW_ISSUE = "/issues/new";

   }
   public static class Templates{
       public static final String INDEX_PAGE = "templates/index";
       public static final String ISSUE_PAGE = "templates/issues";
       public static final String SINGLE_ISSUE_PAGE = "templates/get-issue";
       public static final String ISSUE_COMMENTS_PAGE = "templates/get-issue-comments";
       public static final String LOGIN_PAGE = "templates/login";
       public static final String REGISTRATION = "templates/registration";
       public static final String NEW_ISSUE_PAGE = "templates/new-issue";
   }
}
