package com.scs.web.blog.dao.impl;

import com.scs.web.blog.dao.FollowDao;
import com.scs.web.blog.entity.Topic;
import com.scs.web.blog.entity.User;

import java.sql.SQLException;
import java.util.List;

/**
 * @author zhao
 * @className FollowDaoImpl
 * @Description TODO
 * @Date 2019/12/5
 * @Version 1.0
 **/
public class FollowDaoImpl implements FollowDao {
    @Override
    public List<User> getUserFollows(long userId) throws SQLException {
        return null;
    }

    @Override
    public List<User> getUserFans(long userId) throws SQLException {
        return null;
    }

    @Override
    public List<Topic> getTopicFollows(long topicId) throws SQLException {
        return null;
    }
}
