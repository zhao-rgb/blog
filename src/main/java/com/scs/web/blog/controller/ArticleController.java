package com.scs.web.blog.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.scs.web.blog.domain.vo.ArticleVo;
import com.scs.web.blog.entity.Article;
import com.scs.web.blog.factory.ServiceFactory;
import com.scs.web.blog.service.ArticleService;
import com.scs.web.blog.util.Message;
import com.scs.web.blog.util.ResponseObject;
import com.scs.web.blog.util.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

/**
 * @author zhao
 * @className ArticleController
 * @Description TODO
 * @Date 2019/11/15
 * @Version 1.0
 **/
@WebServlet(urlPatterns = {"/api/article","/api/article/*", "/api/article/detail/*","/api/article/delete/*"})
public class ArticleController extends HttpServlet {
    private static Logger logger = LoggerFactory.getLogger(ArticleController.class);
    private ArticleService articleService = ServiceFactory.getArticleServiceInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String requestPath = req.getRequestURI();
        ResponseObject ro = null;
        List<Article> articleList = null;
        List<ArticleVo> articleVoList = null;
        Article article = null;
        String uri = req.getRequestURI().trim();
        System.out.println(uri);
        if (uri.contains("/api/article/")) {
            String keywords = req.getParameter("keywords");
            if (keywords != null) {
                System.out.println(keywords);
                getArticleByKeywords(resp, keywords);
            }
        }
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
                articleVoList = articleService.articleById(Long.valueOf(id));
                ro = ResponseObject.success(resp.getStatus(), resp.getStatus() == 200 ? "成功" : "失败", articleVoList);
        }
        PrintWriter out = resp.getWriter();
        Gson gson = new GsonBuilder().create();
        out.print(gson.toJson(ro));
        out.close();


    }

    private void getArticleByKeywords(HttpServletResponse resp, String keywords) throws IOException {
        Gson gson = new GsonBuilder().create();
        Result result = articleService.selectByKeywords(keywords);
        PrintWriter out = resp.getWriter();
        out.print(gson.toJson(result));
        out.close();
    }

    @Override
    public void init() throws ServletException {
        logger.info("ArticleController初始化");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String uri = req.getRequestURI().trim();
        System.out.println(uri);
        if (uri.contains("/api/article/new")) {
            Connect(req, resp);

        }

    }

    private void Connect(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        BufferedReader reader = req.getReader();
        StringBuilder stringBuilder = new StringBuilder();
        String line = null;
        while((line = reader.readLine())!=null){
            stringBuilder.append(line);
        }
        System.out.println(stringBuilder.toString());
        //将接受到的客户端JSON字符串转成User对象
        Gson gson = new GsonBuilder().create();
        Article article = gson.fromJson(stringBuilder.toString(),Article.class);
        System.out.println(article);
        Map<String, Object> map =null;
        map = articleService.newarticle(article);
        System.out.println(map);
        String msg = (String) map.get("msg");
        ResponseObject ro = null;
        switch (msg){
            case Message.REGISTER_SUCCESS:
                ro = ResponseObject.success(200,msg,map.get("data"));
        }
        PrintWriter out = resp.getWriter();
        out.print(gson.toJson(ro));
        out.close();
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String uri = req.getRequestURI().trim();
        System.out.println(uri);
        if(uri.contains("/api/article/delete"))
            deleteArticle(req, resp);
    }
    private void deleteArticle(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        String info = req.getPathInfo().trim();
        String id = info.substring(info.indexOf("/") + 1);
        Result result = articleService.deleteArticle(Long.parseLong(id));
        Gson gson = new GsonBuilder().create();
        PrintWriter out = resp.getWriter();
        out.print(gson.toJson(result));
        out.close();

    }
}
