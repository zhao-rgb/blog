package com.scs.web.blog.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.scs.web.blog.domain.dto.UserDto;
import com.scs.web.blog.entity.User;
import com.scs.web.blog.factory.ServiceFactory;
import com.scs.web.blog.listener.MySessionContext;
import com.scs.web.blog.service.UserService;
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
import javax.servlet.http.HttpSession;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author zhao
 * @className UserController
 * @Description TODO
 * @Date 2019/11/9
 * @Version 1.0
 **/
@WebServlet(urlPatterns = {"/api/sign-in","/api/register","/api/user/hot", "/api/detail/*","/api/user"})
public class UserController extends HttpServlet {
    private static Logger logger = LoggerFactory.getLogger(UserController.class);
    private UserService userService = ServiceFactory.getUserServiceInstance();


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //去掉了地址共同的前缀，对不同的部分进行分发
        String uri = req.getRequestURI().trim();
        System.out.println(uri);
        if (uri.contains("/api/user/")) {
            String page = req.getParameter("page");
            String keywords = req.getParameter("keywords");
            String count = req.getParameter("count");
            if (page != null) {
                getUsersByPage(resp, Integer.parseInt(page), Integer.parseInt(count));
            } else if (keywords != null) {
                getUsersByKeywords(resp, keywords);
            } else {
                getHotUser(req, resp);
            }
        } else if (uri.contains("/api/detail/")) {
                getUser(req, resp);
        }
    }

    private void getHotUser(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Gson gson = new GsonBuilder().create();
        Result result = userService.getHotUsers();
        PrintWriter out = resp.getWriter();
        out.print(gson.toJson(result));
        out.close();
    }


    private void getUsersByPage(HttpServletResponse resp, int page, int count) throws IOException {
        Gson gson = new GsonBuilder().create();
        Result result = userService.selectByPage(page, count);
        PrintWriter out = resp.getWriter();
        out.print(gson.toJson(result));
        out.close();
    }

    private void getUsersByKeywords(HttpServletResponse resp, String keywords) throws IOException {
        Gson gson = new GsonBuilder().create();
        Result result = userService.selectByKeywords(keywords);
        PrintWriter out = resp.getWriter();
        out.print(gson.toJson(result));
        out.close();
    }


    private void getUser(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        ResponseObject ro = null;
        List<Object> list = null;
        String requestPath = req.getRequestURI().trim();
        //取得路径参数
        String id = requestPath.substring(requestPath.lastIndexOf("/") + 1);
        Gson gson = new GsonBuilder().create();
        list = userService.userById(Long.valueOf(id));
        ro = ResponseObject.success(resp.getStatus(), resp.getStatus() == 200 ? "成功" : "失败", list);
        PrintWriter out = resp.getWriter();
        out.print(gson.toJson(ro));
        out.close();
    }



    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        BufferedReader reader = req.getReader();
        StringBuilder stringBuilder = new StringBuilder();
        String line = null;
        while ((line = reader.readLine()) != null) {
            stringBuilder.append(line);
        }
        logger.info("登录用户信息：" + stringBuilder.toString());
        Gson gson = new GsonBuilder().create();
        UserDto userDto = gson.fromJson(stringBuilder.toString(), UserDto.class);

        String inputCode = userDto.getCode().trim();

        Map<String, Object> map = null;
        String sessionId = req.getHeader("Access-Token");
        System.out.println("客户端传来的JSESSIONID：" + sessionId);
        MySessionContext myc = MySessionContext.getInstance();
        HttpSession session = myc.getSession(sessionId);
        String correctCode = session.getAttribute("code").toString().replace("  ", "");
        System.out.println("正确的验证码：" + correctCode);
        // 获取请求路径
        String requestPath = req.getRequestURI().trim();
        System.out.println(requestPath);
        PrintWriter out = resp.getWriter();
        switch (requestPath) {
            case "/api/sign-in":
                System.out.println("进入登录");
                map = userService.signIn(userDto);
                if (!inputCode.equalsIgnoreCase(correctCode)) {
                    map.put("msg", "验证码错误");
                }
                break;
            case "/api/register":
                System.out.println("进入注册");
                map = userService.register(userDto);
                break;
        }
        String msg = (String) map.get("msg");
        ResponseObject ro;
        switch (msg) {
            case Message.SIGN_IN_SUCCESS:
            case Message.REGISTER_SUCCESS:
                ro = ResponseObject.success(200, msg, map.get("data"));
                break;
            case Message.PASSWORD_ERROR:
            case Message.MOBILE_NOT_FOUND:
            case Message.REGISTER_DEFEATED:
            default:
                ro = ResponseObject.success(200, msg);
        }


        out.print(gson.toJson(ro));
        out.close();
    }
    @Override
    public void init() throws ServletException {
        logger.info("UserController初始化");
    }
}
