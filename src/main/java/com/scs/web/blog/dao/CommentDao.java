package com.scs.web.blog.dao;

import com.scs.web.blog.domain.dto.CommentDto;
import com.scs.web.blog.domain.vo.CommentVo;
import com.scs.web.blog.entity.Comment;

import java.sql.SQLException;
import java.util.List;

/**
 * @author zhao
 * @className CommentDao
 * @Description TODO
 * @Date 2019/12/5
 * @Version 1.0
 **/
public interface CommentDao {
    /**
     * 写评论
     * @param commentDto
     * @return
     * @throws SQLException
     */
    int insert(CommentDto commentDto) throws SQLException;

    /**
     *查看所有评论
     * @return
     * @throws SQLException
     */
    List<Comment> selectAll() throws SQLException;

    /**
     *得到评论的东西
     * @param articleId
     * @return
     * @throws SQLException
     */
    List<CommentVo> getComment(long articleId) throws SQLException;
//    /**
//     * 分页显示评论信息
//     * @param currentPage
//     * @return
//     * @throws SQLException
//     */
//     List<Comment> selectByPage(int currentPage, int count)throws SQLException;
    /**
     *删除评论
     * @param id
     * @return
     * @throws SQLException
     */
    int delete(long id) throws SQLException;

    /**
     * 更新评论数
     * @param id
     * @return
     * @throws SQLException
     */
    int updatecommnet(long id) throws SQLException;
}
