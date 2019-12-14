package com.scs.web.blog.factory;

import com.scs.web.blog.service.*;
import com.scs.web.blog.service.impl.*;

/**
 * @author zhao
 * @className ServiceFactory
 * @Description TODO
 * @Date 2019/11/7
 * @Version 1.0
 **/
public class ServiceFactory {
    public static UserService getUserServiceInstance(){
        return new UserServiceImpl();
    }
    public static ArticleService getArticleServiceInstance() {
        return new ArticleServiceImpl();
    }
    public static TopicService getTopicServiceInstance() {
        return new TopicServiceImpl();
    }
    public static CommentService getCommentServiceInstance(){
        return  new CommentServiceImpl();
    }
    public static LikeService getLikeServiceInstance(){
        return new LikeServiceImpl();
    }
}
