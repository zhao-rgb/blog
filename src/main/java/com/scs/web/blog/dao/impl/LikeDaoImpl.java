package com.scs.web.blog.dao.impl;

import com.scs.web.blog.dao.LikeDao;
import com.scs.web.blog.entity.Like;
import com.scs.web.blog.util.DbUtil;
import com.scs.web.blog.util.Result;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author zhao
 * @className LikeDaoImpl
 * @Description TODO
 * @Date 2019/12/13
 * @Version 1.0
 **/
public class LikeDaoImpl implements LikeDao {

    @Override
    public boolean insertLike(long useId, long articleId) throws SQLException {
        Connection connection = DbUtil.getConnection();
        String sql = "INSERT INTO t_like (user_id, article_id) VALUES (?, ?)";
        PreparedStatement pst = connection.prepareStatement(sql);
        pst.setLong(1, useId);
        pst.setLong(2, articleId);
        int result = pst.executeUpdate();
        if (result > 0){
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean deleteLike(long useId, long articleId) throws SQLException {
        Connection connection = DbUtil.getConnection();
        String sql = "DELETE FROM t_like WHERE user_id=? AND article_id=?";
        PreparedStatement pstmt = connection.prepareStatement(sql);
        pstmt.setLong(1, useId);
        pstmt.setLong(2, articleId);
        int result = pstmt.executeUpdate();
        if (result > 0) {
            return true;
        } else {
            return false;
        }
    }

}
