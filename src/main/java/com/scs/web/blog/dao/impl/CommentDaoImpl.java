package com.scs.web.blog.dao.impl;

import com.scs.web.blog.dao.CommentDao;
import com.scs.web.blog.domain.dto.CommentDto;
import com.scs.web.blog.entity.Comment;
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
        String sql = "INSERT INTO t_comment(nickname,content,create_time) VALUES(?,?,?)";
        PreparedStatement pst = connection.prepareStatement(sql);
        pst.setString(1,commentDto.getNickname());
        pst.setString(2,commentDto.getContent());
        pst.setObject(3, Timestamp.valueOf(LocalDateTime.now()));
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

}
