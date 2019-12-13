package com.scs.web.blog.dao.impl;

import com.scs.web.blog.dao.CommentDao;
import com.scs.web.blog.domain.dto.CommentDto;
import com.scs.web.blog.domain.vo.CommentVo;
import com.scs.web.blog.entity.Comment;
import com.scs.web.blog.entity.User;
import com.scs.web.blog.service.impl.CommentServiceImpl;
import com.scs.web.blog.util.DbUtil;
import com.scs.web.blog.util.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @author zhao
 * @className CommentDaoImpl
 * @Description TODO
 * @Date 2019/12/5
 * @Version 1.0
 **/
public class CommentDaoImpl implements CommentDao {
    private static Logger logger = LoggerFactory.getLogger(CommentServiceImpl.class);
    @Override
    public int insert(CommentDto commentDto) throws SQLException {
        Connection connection = DbUtil.getConnection();
        String sql = "INSERT INTO t_comment(user_id,article_id,content,create_time) VALUES(?,?,?,?)";
        PreparedStatement pst = connection.prepareStatement(sql);
        pst.setLong(1,commentDto.getUserId());
        pst.setLong(2,commentDto.getArticleId());
        pst.setString(3,commentDto.getContent());
        pst.setObject(4, Timestamp.valueOf(LocalDateTime.now()));
        int i = pst.executeUpdate();
        System.out.println("行数为" +i);
        return i;
    }

    @Override
    public List<Comment> selectAll() throws SQLException {
        List<Comment> commentList = new ArrayList<>();
        Connection connection = DbUtil.getConnection();
        String sql = "SELECT * FROM t_comment ORDER BY id DESC ";
        PreparedStatement pst = connection.prepareStatement(sql);
        ResultSet rs = pst.executeQuery();
        while (rs.next()) {
            Comment comment = new Comment();
            comment.setId(rs.getLong("id"));
            comment.setUserId(rs.getLong("user_id"));
            comment.setArticleId(rs.getLong("article_id"));
            comment.setNickname(rs.getString("nickname"));
            comment.setContent(rs.getString("content"));
            Timestamp timestamp = rs.getTimestamp("create_time");
            comment.setCreateTime(timestamp.toLocalDateTime());
            commentList.add(comment);
        }
        return commentList;
    }

    @Override
    public List<CommentVo> getComment(long articleId) throws SQLException {
        Connection connection = DbUtil.getConnection();
        String sql = "SELECT a.*,b.avatar,b.nickname FROM t_comment a LEFT JOIN t_user b ON a.user_id = b.id WHERE a.article_id = ?";
        PreparedStatement pst= connection.prepareStatement(sql);
        pst.setLong(1,articleId);
        ResultSet rs = pst.executeQuery();
        CommentVo commentVo = null;
        List<CommentVo> commentVoList = new ArrayList<>();
       while (rs.next()){
            Comment comment = new Comment();
            comment.setId(rs.getLong("id"));
            comment.setUserId(rs.getLong("user_id"));
            comment.setArticleId(rs.getLong("article_id"));
            comment.setContent(rs.getString("content"));
            Timestamp timestamp = rs.getTimestamp("create_time");
            comment.setCreateTime(timestamp.toLocalDateTime());

            //作者信息
            User author = new User();
            author.setNickname(rs.getString("nickname"));
            author.setAvatar(rs.getString("avatar"));

            commentVo = new CommentVo();
            commentVo.setAuthor(author);
            commentVo.setComment(comment);
            commentVoList.add(commentVo);
        }
        return commentVoList;
    }

    @Override
    public int delete(long id) throws SQLException {
        Connection connection = DbUtil.getConnection();
        String sql = "DELETE FROM t_comment WHERE id = ? ";
        PreparedStatement pst = connection.prepareStatement(sql);
        pst.setLong(1,id);
        int i = pst.executeUpdate();
        System.out.println("行数为:" + i);
        return i;
    }
}
