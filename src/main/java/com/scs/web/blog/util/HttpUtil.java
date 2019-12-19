package com.scs.web.blog.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author zhao
 * @className HttpUtil
 * @Description TODO
 * @Date 2019/12/19
 * @Version 1.0
 **/
public class HttpUtil {
    private static Logger logger = LoggerFactory.getLogger(HttpUtil.class);

    /**
     * 根据请求对象得到缓冲字符输入流，按行读取请求体内容，构建成JSON字符串
     *
     * @param req
     * @return
     */
    public static String getRequestBody(HttpServletRequest request) {
        StringBuilder stringBuilder = new StringBuilder();
        try {
            BufferedReader reader = request.getReader();
            String line;
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line);
            }
        } catch (IOException e) {
            logger.error("读取请求JSON内容异常");
        }
        return stringBuilder.toString();
    }

    /**
     * 将请求信息通过json的形式输出给客户端
     *
     * @param out
     * @param result
     */
    public static void getResponseBody(HttpServletResponse response, Result result) {
        Gson gson = new GsonBuilder().create();
        PrintWriter out = null;
        try {
            out = response.getWriter();
            out.print(gson.toJson(result));
            out.close();
        } catch (IOException e) {
            logger.error("响应对象输出异常");
        }
    }

    /**
     * 获取路径参数
     * @param request
     * @return
     */
    public static String getPathParam(HttpServletRequest request){
        String param = request.getPathInfo().trim();
        return param.substring(param.indexOf("/") + 1);
    }
}
