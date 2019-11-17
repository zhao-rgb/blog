package com.scs.web.blog.dao.impl;

import com.scs.web.blog.entity.Topic;

import java.sql.SQLException;
import java.util.List;

/**
 * @author zhao
 * @className TopicDao
 * @Description TODO
 * @Date 2019/11/15
 * @Version 1.0
 **/
public interface TopicDao {
    int[] batchInsert(List<Topic> topicList) throws SQLException;
}
