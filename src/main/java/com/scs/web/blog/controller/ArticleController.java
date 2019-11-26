package com.scs.web.blog.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.scs.web.blog.domain.vo.ArticleVo;
import com.scs.web.blog.entity.Article;
import com.scs.web.blog.factory.ServiceFactory;
import com.scs.web.blog.service.ArticleService;
import com.scs.web.blog.util.ResponseObject;
import com.scs.web.blog.util.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

/**
 * @author zhao
 * @className ArticleController
 * @Description TODO
 * @Date 2019/11/15
 * @Version 1.0
 **/
@WebServlet(urlPatterns = {"/api/article","/api/article/hot", "/api/article/detail/*"})
public class ArticleController extends HttpServlet {
    private static Logger logger = LoggerFactory.getLogger(ArticleController.class);
    private ArticleService articleService = ServiceFactory.getArticleServiceInstance();

//    @Override
//    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        Gson gson = new GsonBuilder().create();
//        ResponseObject ro = ResponseObject.success(200, "成功", articleService.getHotArticles());
//        PrintWriter out = resp.getWriter();
//        out.print(gson.toJson(ro));
//        out.close();
//    }
//    private void getHotArticles(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        Gson gson = new GsonBuilder().create();
//        Result result = (Result) articleService.getHotArticles();
//        PrintWriter out = resp.getWriter();
//        out.print(gson.toJson(result));
//        out.close();
//    }
//
//    private void getArticlesByPage(HttpServletResponse resp, int page, int count) throws ServletException, IOException {
//        Gson gson = new GsonBuilder().create();
//        Result result = articleService.getArticlesByPage(page, count);
//        PrintWriter out = resp.getWriter();
//        out.print(gson.toJson(result));
//        out.close();
//    }
//
//    private void getArticlesByKeywords(HttpServletResponse resp, String keywords) throws ServletException, IOException {
//        Gson gson = new GsonBuilder().create();
//        Result result = articleService.selectByKeywords(keywords);
//        PrintWriter out = resp.getWriter();
//        out.print(gson.toJson(result));
//        out.close();
//    }

//    private void getArticle(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        String info = req.getPathInfo().trim();
//        //取得路径参数
//        String id = info.substring(info.indexOf("/") + 1);
//        Result result = articleService.getArticle(Long.parseLong(id));
//        Gson gson = new GsonBuilder().create();
//        PrintWriter out = resp.getWriter();
//        out.print(gson.toJson(result));
//        out.close();
//    }



    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String requestPath = req.getRequestURI();
        ResponseObject ro = null;
        List<Article> articleList = null;
        List<ArticleVo> articleVoList = null;
        Article article = null;
        switch (requestPath) {
            case "/api/article":
                articleList = articleService.initArticle();
                ro = ResponseObject.success(resp.getStatus(), resp.getStatus() == 200 ? "成功" : "失败", articleList);
                break;
            case "/api/article/hot":
                articleVoList = articleService.getHotArticles();
                ro = ResponseObject.success(resp.getStatus(), resp.getStatus() == 200 ? "成功" : "失败", articleVoList);
                break;
            default:
                String id = requestPath.substring(requestPath.lastIndexOf("/") + 1);
                article = articleService.articleById(Long.valueOf(id));
                ro = ResponseObject.success(resp.getStatus(), resp.getStatus() == 200 ? "成功" : "失败", article);
        }
        PrintWriter out = resp.getWriter();
        Gson gson = new GsonBuilder().create();
        out.print(gson.toJson(ro));
        out.close();
    }

    @Override
    public void init() throws ServletException {
        logger.info("ArticleController初始化");
    }
}
