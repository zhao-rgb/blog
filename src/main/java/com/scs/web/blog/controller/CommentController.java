package com.scs.web.blog.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.scs.web.blog.dao.CommentDao;
import com.scs.web.blog.domain.dto.CommentDto;
import com.scs.web.blog.entity.Comment;
import com.scs.web.blog.factory.DaoFactory;
import com.scs.web.blog.factory.ServiceFactory;
import com.scs.web.blog.service.CommentService;
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
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * @author zhao
 * @className CommentController
 * @Description TODO
 * @Date 2019/12/9
 * @Version 1.0
 **/
@WebServlet(urlPatterns = {"/api/comment","/api/comment/*","/api/comments","/api/comments/delete/*"})
public class CommentController extends HttpServlet {
    private static Logger logger = LoggerFactory.getLogger(CommentController.class);
    private CommentDao commentDao = DaoFactory.getCommentDaoInstance();
    private CommentService commentService = ServiceFactory.getCommentServiceInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String uri = req.getRequestURI().trim();
        System.out.println(uri);
        if(uri.contains("/api/comments")){
            listComment(req, resp);
        }else if(uri.contains("/api/comment")){
            getComment(req, resp);
        }

    }



    private void listComment(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Comment> comments = commentService.listComment();
        Gson gson = new Gson();
        resp.setContentType("application/json;charset=UTF-8");
        resp.setCharacterEncoding("UTF-8");
        PrintWriter out = resp.getWriter();
        ResponseObject ro = null;
        ro = ResponseObject.success(resp.getStatus(), resp.getStatus() == 200 ? "成功" : "失败", comments);
        out.print(gson.toJson(ro));
        out.close();
    }
    private void getComment(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String info = req.getPathInfo().trim();
        String id = info.substring(info.indexOf("/") + 1);
        Result result = commentService.getComment(Long.parseLong(id));
        Gson gson = new GsonBuilder().create();
        PrintWriter out = resp.getWriter();
        out.print(gson.toJson(result));
        out.close();
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String uri = req.getRequestURI().trim();
        System.out.println(uri);
        if (uri.contains("/api/comment")) {
            Connect(req, resp);

        }
    }

    private void Connect(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        //请求字符集设置
        req.setCharacterEncoding("UTF-8");
        //接送客户端船体的Json数据，通过缓冲字符流按行读取，存入可变长字符串中
        BufferedReader reader = req.getReader();
        StringBuilder stringBuilder = new StringBuilder();
        String line = null;
        while((line = reader.readLine())!=null){
            stringBuilder.append(line);
        }
        System.out.println(stringBuilder.toString());
        //将接受到的客户端JSON字符串转成User对象
        Gson gson = new GsonBuilder().create();
        CommentDto commentDto =gson.fromJson(stringBuilder.toString(),CommentDto.class);
        System.out.println(commentDto);
        //插入数据库，并返回该行主键
        Map<String, Object> map = null;
        map= commentService.newcomment(commentDto);
        System.out.println(map);
        String msg = (String) map.get("msg");
        ResponseObject ro=null;
        switch (msg){

            case Message.REGISTER_SUCCESS:
                ro = ResponseObject.success(200, msg, map.get("data"));
//            default:
//                ro = ResponseObject.success(200, msg);
        }


        //补全user的id字段信息
        //通过response对象返回Json信息
        PrintWriter out = resp.getWriter();
        out.print(gson.toJson(ro));
        out.close();
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String uri = req.getRequestURI().trim();
        System.out.println(uri);
        if(uri.contains("/api/comments/delete"))
            deleteComment(req, resp);
    }
//    private void getCommentByPage(HttpServletResponse resp, int page, int count) throws ServletException, IOException {
//        Gson gson = new GsonBuilder().create();
//        Result result = CommentService.selectByPage(page, count);
//        PrintWriter out = resp.getWriter();
//        out.print(gson.toJson(result));
//        out.close();
//    }
    private void deleteComment(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Gson gson = new GsonBuilder().create();
//        String info = req.getPathInfo().trim();
//        String id = info.substring(info.indexOf("/") + 1);
        String id = req.getParameter("id");
        String articleId = req.getParameter("articleId");
        Result result = commentService.deleteComment(Long.valueOf(id),Long.valueOf(articleId));
        PrintWriter out = resp.getWriter();
        out.print(gson.toJson(result));
        out.close();
    }
}
