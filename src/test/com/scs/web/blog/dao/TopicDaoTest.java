package com.scs.web.blog.dao;

import com.scs.web.blog.entity.Topic;
import com.scs.web.blog.factory.DaoFactory;
import com.scs.web.blog.util.JSoupSpider;
import org.junit.Test;

import java.sql.SQLException;
import java.util.List;

import static org.junit.Assert.*;

public class TopicDaoTest {
    private TopicDao topicDao = DaoFactory.getTopicDaoInstance();

    @Test
    public void batchInsert() throws SQLException {
        int[] result = topicDao.batchInsert(JSoupSpider.getTopics());
        System.out.println(result.length);
    }

    @Test
    public void selectByKeywords() throws SQLException{
        List<Topic> topicList = topicDao.selectByKeywords("小");
        System.out.println(topicList.size());
    }

    @Test
    public void getTopic() {

    }
}