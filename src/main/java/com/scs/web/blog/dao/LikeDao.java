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
     * @param useId
     * @param articleId
     * @return
     * @throws SQLException
     */
    boolean insertLike(long useId, long articleId) throws SQLException;

    /**
     * 删除一条喜欢
     * @param useId
     * @param articleId
     * @return
     * @throws SQLException
     */
    boolean deleteLike(long useId, long articleId) throws  SQLException;

}
