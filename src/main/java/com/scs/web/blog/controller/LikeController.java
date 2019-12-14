package com.scs.web.blog.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.scs.web.blog.entity.Like;
import com.scs.web.blog.factory.ServiceFactory;
import com.scs.web.blog.service.LikeService;
import com.scs.web.blog.util.Result;

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
@WebServlet(urlPatterns = "/api/like")
public class LikeController extends HttpServlet {
    private LikeService  likeService = ServiceFactory.getLikeServiceInstance();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        BufferedReader reader = req.getReader();
        StringBuilder stringBuilder = new StringBuilder();
        String line ;
        while ((line = reader.readLine()) != null) {
            stringBuilder.append(line);
        }
        Gson gson = new GsonBuilder().create();
        Like like = gson.fromJson(stringBuilder.toString(), Like.class);
        Result result = likeService.addLike(like);
        PrintWriter out = resp.getWriter();
        out.print(gson.toJson(result));
        out.close();
    }

}
