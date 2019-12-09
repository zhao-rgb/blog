package com.scs.web.blog.dao;

import com.scs.web.blog.domain.dto.CommentDto;
import com.scs.web.blog.entity.Comment;

import java.sql.SQLException;

/**
 * @author zhao
 * @className CommentDao
 * @Description TODO
 * @Date 2019/12/5
 * @Version 1.0
 **/
public interface CommentDao {
    /**
     * 评论
     * @param commentDto
     * @return
     * @throws SQLException
     */
    int insert(CommentDto commentDto) throws SQLException;
}
