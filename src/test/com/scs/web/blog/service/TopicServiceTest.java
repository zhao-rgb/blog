package com.scs.web.blog.service;

import com.scs.web.blog.factory.ServiceFactory;
import com.scs.web.blog.util.Result;
import org.junit.Test;



public class TopicServiceTest {
 private TopicService topicService = ServiceFactory.getTopicServiceInstance();
    @Test
    public void selectByKeywords() {
        Result rs = topicService.selectByKeywords("小");
        System.out.println(rs.getData());
    }
}