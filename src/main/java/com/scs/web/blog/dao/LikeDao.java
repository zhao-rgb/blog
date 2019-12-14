package com.scs.web.blog.dao;

import com.scs.web.blog.entity.Like;

import java.sql.SQLException;

/**
 * @author zhao
 * @className LikeDao
 * @Description TODO
 * @Date 2019/12/13
 * @Version 1.0
 **/
public interface LikeDao {
    /**
     * 写入一条喜欢
     * @param like
     * @return
     * @throws SQLException
     */
    boolean insertLike(Like like) throws SQLException;
    /**
     * 查看文章数
     * @param articleId
     * @return
     * @throws SQLException
     */
    long selectLikes(long articleId) throws SQLException;

    /**
     * 点赞文章
     * @param articleId
     * @return
     * @throws SQLException
     */
    boolean addLikes(long articleId) throws SQLException;
}
