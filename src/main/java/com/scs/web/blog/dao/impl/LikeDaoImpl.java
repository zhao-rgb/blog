package com.scs.web.blog.dao.impl;

import com.scs.web.blog.dao.LikeDao;
import com.scs.web.blog.domain.vo.LikeVo;
import com.scs.web.blog.entity.Article;
import com.scs.web.blog.entity.Like;
import com.scs.web.blog.util.DbUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author zhao
 * @className LikeDaoImpl
 * @Description TODO
 * @Date 2019/12/13
 * @Version 1.0
 **/
public class LikeDaoImpl implements LikeDao {
    @Override
    public Like selectByUserId(long userId) throws SQLException {
        Connection connection = DbUtil.getConnection();
        String sql = "SELECT * FROM t_like WHERE user_id = ?";
        PreparedStatement pst = connection.prepareStatement(sql);
        pst.setLong(1,userId);
        ResultSet rs = pst.executeQuery();
        Like like =getLike(userId);
        return like;
    }

    @Override
    public Like getLike(long userId) throws SQLException {
        Connection connection = DbUtil.getConnection();
        String sql = "SELECT * FROM t_like WHERE user_id = ?";
        PreparedStatement pst = connection.prepareStatement(sql);
        pst.setLong(1,userId);
        ResultSet rs = pst.executeQuery();
        Like like = null;
        while(rs.next()){
            like = new Like();
            like.setId(rs.getLong("id"));
            like.setUserId(rs.getLong("user_id"));
            like.setArticleId(rs.getLong("article_id"));
        }
        return like;
    }

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
        PreparedStatement pst = connection.prepareStatement(sql);
        pst.setLong(1, useId);
        pst.setLong(2, articleId);
        int result = pst.executeUpdate();
        if (result > 0) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public List<LikeVo> getLikes(long userId) throws SQLException {
        Connection connection = DbUtil.getConnection();
        String sql = "SELECT a.*,b.title,b.cover FROM t_like a LEFT JOIN t_article b ON a.article_id = b.id WHERE a.user_id = ?";
        PreparedStatement pst = connection.prepareStatement(sql);
        pst.setLong(1,userId);
        ResultSet rs = pst.executeQuery();
        LikeVo likeVo = null;
        List<LikeVo> likeVoList = new ArrayList<>();
        while(rs.next()){
            Like like = new Like();
            like.setId(rs.getLong("id"));
            like.setUserId(rs.getLong("user_id"));
            like.setArticleId(rs.getLong("article_id"));

            //点赞文章的信息
            Article article = new Article();
            article.setTitle(rs.getString("title"));
            article.setCover(rs.getString("cover"));

            likeVo =new LikeVo();
            likeVo.setArticle(article);
            likeVo.setLike(like);
            likeVoList.add(likeVo);
        }
        return likeVoList;
    }
}
