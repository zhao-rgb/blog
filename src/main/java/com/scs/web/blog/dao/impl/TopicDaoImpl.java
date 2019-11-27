package com.scs.web.blog.dao.impl;


import com.scs.web.blog.dao.TopicDao;

import com.scs.web.blog.domain.vo.TopicVo;
import com.scs.web.blog.entity.Topic;
import com.scs.web.blog.util.DbUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author zhao
 * @className TopicDaoImpl
 * @Description TODO
 * @Date 2019/11/17
 * @Version 1.0
 **/
public class TopicDaoImpl implements TopicDao {
    private static Logger logger = LoggerFactory.getLogger(TopicDaoImpl.class);

    @Override
    public int[] batchInsert(List<Topic> topicList) throws SQLException {
        Connection connection = DbUtil.getConnection();
        connection.setAutoCommit(false);
        String sql = "INSERT INTO t_topic (admin_id,topic_name,logo,description,articles,follows,create_time) VALUES (?,?,?,?,?,?,?) ";
        PreparedStatement pstmt = connection.prepareStatement(sql);
        topicList.forEach(topic -> {
            try {
                pstmt.setLong(1, topic.getAdminId());
                pstmt.setString(2, topic.getTopicName());
                pstmt.setString(3, topic.getLogo());
                pstmt.setString(4, topic.getDescription());
                pstmt.setInt(5, topic.getArticles());
                pstmt.setInt(6, topic.getFollows());
                pstmt.setObject(7, topic.getCreateTime());
                pstmt.addBatch();
            } catch (SQLException e) {
                logger.error("批量加入专题数据产生异常");
            }
        });
        int[] result = pstmt.executeBatch();
        connection.commit();
//        DbUtil.close(null, pstmt, connection);
        return result;
    }

    @Override
    public List<Topic> selectHotTopics() throws SQLException {
        Connection connection = DbUtil.getConnection();
        String sql = "SELECT * FROM t_topic ORDER BY follows DESC LIMIT 10 ";
        PreparedStatement pstmt = connection.prepareStatement(sql);
        ResultSet rs = pstmt.executeQuery();
        List<Topic> topicList = convertTopic(rs);
        return topicList;
    }

    @Override
    public List<Topic> selectByPage(int currentPage, int count) throws SQLException {
        Connection connection = DbUtil.getConnection();
        String sql = "SELECT * FROM t_topic " +
                "ORDER BY id  LIMIT ?,? ";
        PreparedStatement pstmt = connection.prepareStatement(sql);
        pstmt.setInt(1, (currentPage - 1) * count);
        pstmt.setInt(2, count);
        ResultSet rs = pstmt.executeQuery();
        List<Topic> topicList = convertTopic(rs);
        return topicList;
    }


    @Override
    public TopicVo getTopic(long id) throws SQLException {
        Connection connection = DbUtil.getConnection();
        String sql = "SELECT a.*,b.nickname,b.avatar " +
                "FROM t_topic a " +
                "LEFT JOIN t_user b " +
                "ON a.admin_id = b.id " +
                "WHERE a.id = ?  ";
        PreparedStatement pstmt = connection.prepareStatement(sql);
        pstmt.setLong(1, id);
        ResultSet rs = pstmt.executeQuery();
        TopicVo topicVo = null;
        if (rs.next()) {
            topicVo = new TopicVo();
            topicVo.setId(rs.getLong("id"));
            topicVo.setAdminId(rs.getLong("admin_id"));
            topicVo.setTopicName(rs.getString("topic_name"));
            topicVo.setLogo(rs.getString("logo"));
            topicVo.setDescription(rs.getString("description"));
            topicVo.setArticles(rs.getInt("articles"));
            topicVo.setFollows(rs.getInt("follows"));
            topicVo.setCreateTime(rs.getTimestamp("create_time").toLocalDateTime());
        }
        return topicVo;
    }

    @Override
    public List<Topic> selectByKeywords(String keywords) throws SQLException {
        Connection connection = DbUtil.getConnection();
        String sql = "SELECT * FROM t_topic " +
                "WHERE name LIKE ?  OR description LIKE ? ";
        PreparedStatement pstmt = connection.prepareStatement(sql);
        pstmt.setString(1, "%" + keywords + "%");
        pstmt.setString(2, "%" + keywords + "%");
        ResultSet rs = pstmt.executeQuery();
        List<Topic> topicList = convertTopic(rs);
//        DbUtil.close(null, pstmt, connection);
        return topicList;
    }

    private List<Topic> convertTopic(ResultSet rs) {
        List<Topic> topicList = new ArrayList<>(50);
        try {
            while (rs.next()) {
                Topic topic = new Topic();
                topic.setId(rs.getLong("id"));
                topic.setAdminId(rs.getLong("admin_id"));
                topic.setTopicName(rs.getString("topic_name"));
                topic.setLogo(rs.getString("logo"));
                topic.setDescription(rs.getString("description"));
                topic.setArticles(rs.getInt("articles"));
                topic.setFollows(rs.getInt("follows"));
                topic.setCreateTime(rs.getTimestamp("create_time").toLocalDateTime());
                topicList.add(topic);
            }
        } catch (SQLException e) {
            logger.error("查询专题数据产生异常");
        }
        return topicList;
    }
}
