package com.scs.web.blog.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.scs.web.blog.entity.Like;
import com.scs.web.blog.factory.ServiceFactory;
import com.scs.web.blog.service.LikeService;
import com.scs.web.blog.util.Result;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author zhao
 * @className LikeController
 * @Description TODO
 * @Date 2019/12/14
 * @Version 1.0
 **/
@WebServlet(urlPatterns = {"/api/like","/api/like/delete"})
@Slf4j
public class LikeController extends HttpServlet {
    private LikeService  likeService = ServiceFactory.getLikeServiceInstance();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Gson gson = new GsonBuilder().create();
        String userId = req.getParameter("userId");
        String articleId = req.getParameter("articleId");
        System.out.println(userId+"  "+articleId);
        Result result = likeService.addLike(Long.valueOf(userId), Long.valueOf(articleId));
        PrintWriter out = resp.getWriter();
        out.print(gson.toJson(result));
        out.close();
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Gson gson = new GsonBuilder().create();
        String userId = req.getParameter("userId");
        String articleId = req.getParameter("articleId");
        System.out.println(userId+"  "+articleId);
        Result result = likeService.lessenLike(Long.valueOf(userId), Long.valueOf(articleId));
        PrintWriter out = resp.getWriter();
        out.print(gson.toJson(result));
        out.close();
    }
}
