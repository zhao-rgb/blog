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
    public boolean insertLike(Like like) throws SQLException {
        Connection connection = DbUtil.getConnection();
        String sql = "INSERT INTO t_like (user_id, article_id) VALUES (?, ?)";
        PreparedStatement pstmt = connection.prepareStatement(sql);
        pstmt.setLong(1, like.getUserId());
        pstmt.setLong(2, like.getArticleId());
        int n = pstmt.executeUpdate();
        DbUtil.close(connection, pstmt);
        if (n == 1){
            return true;
        } else {
            return false;
        }
    }

    @Override
    public long selectLikes(long articleId) throws SQLException {
        Connection connection = DbUtil.getConnection();
        String sql = "SELECT likes FROM t_article WHERE id = ?";
        PreparedStatement pst = connection.prepareStatement(sql);
        pst.setLong(1,articleId);
        ResultSet rs = pst.executeQuery();
        long likes = 0;
        while(rs.next()){
            likes = rs.getLong("likes");
        }
        return likes;
    }

    @Override
    public boolean addLikes(long articleId) throws SQLException {
        Connection connection = DbUtil.getConnection();
        String sql = "UPDATE t_article SET likes = ? WHERE id =?";
        PreparedStatement pst = connection.prepareStatement(sql);
        pst.setLong(1,selectLikes(articleId) +1);
        pst.setLong(2,articleId);
        int result = pst.executeUpdate();
        if(result == 1){
            return true;
        }else {
            return false;
        }
    }
}
