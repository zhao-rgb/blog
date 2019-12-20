package com.scs.web.blog.controller;

import com.scs.web.blog.util.AliOssUtil;
import com.scs.web.blog.util.HttpUtil;
import com.scs.web.blog.util.Result;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;

/**
 * @author zhao
 * @className UploadController
 * @Description TODO
 * @Date 2019/11/21
 * @Version 1.0
 **/
@MultipartConfig(maxFileSize = 1024 * 1024 *50)
@WebServlet(urlPatterns = "/api/upload")
public class UploadController extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Part part = req.getPart("file");
        //取得客户端提交的文件名
        String name = part.getSubmittedFileName();
        System.out.println("name");
        String path = req.getSession().getServletContext().getRealPath("");
        System.out.println(path);
        part.write(path + name);
        System.out.println(path + name);
        File file = new File(path + name);
        String url = AliOssUtil.upload(file);
        System.out.println(url);
        HttpUtil.getResponseBody(resp, Result.success(url));
    }
}
