package com.scs.web.blog.dao;

import com.scs.web.blog.domain.vo.TopicVo;
import com.scs.web.blog.entity.Topic;

import java.sql.SQLException;
import java.util.List;

/**
 * @author zhao
 * @className TopicDao
 * @Description TODO
 * @Date 2019/11/17
 * @Version 1.0
 **/
public interface TopicDao {
    /**
     * 批量新增专题
     *
     * @param topicList
     * @return
     * @throws SQLException
     */
    int[] batchInsert(List<Topic> topicList) throws SQLException;

    /**
     * 获取热门专题
     * @return
     * @throws SQLException
     */
    List<Topic> selectHotTopics() throws SQLException;

    /**
     * 分页显示专题信息
     * @param currentPage
     * @return
     * @throws SQLException
     */
    List<Topic> selectByPage(int currentPage,int count)throws SQLException;

    /**
     * 根据id获取专题详情
     * @param id
     * @return
     * @throws SQLException
     */
    TopicVo getTopic(long id)throws SQLException;

    /**
     * 模糊搜索专题
     * @param keywords
     * @return
     * @throws SQLException
     */
    List<Topic> selectByKeywords(String keywords) throws SQLException;

}
