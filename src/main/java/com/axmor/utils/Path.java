package com.axmor.utils;

public class Path {

   public static class Web {
       public static final String INDEX = "/";
       public static final String ARTICLES = "/articles";
       public static final String ONE_ARTICLE = "/articles/art";
       public static final String COMMENTS = "/articles/comment";
       public static final String LOGIN = "/login";
       public static final String LOGOUT = "/logout";
       public static final String REGISTRATION = "/registration";
       public static final String REGISTRATION_SAVE = "/registration/save";
       public static final String NEW_ARTICLE = "/articles/new";

   }
   public static class Templates{
       public static final String INDEX_PAGE = "templates/index";
       public static final String ARTICLE_PAGE = "templates/articles";
       public static final String SINGLE_ARTICLE_PAGE = "templates/get-article";
       public static final String ARTICLE_COMMENTS_PAGE = "templates/get-article-comments";
       public static final String LOGIN_PAGE = "templates/login";
       public static final String REGISTRATION = "templates/registration";
       public static final String NEW_ARTICLE_PAGE = "templates/new-article";
   }
}
