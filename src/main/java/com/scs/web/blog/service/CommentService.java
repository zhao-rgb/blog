package com.scs.web.blog.service;

import com.scs.web.blog.domain.dto.CommentDto;
import com.scs.web.blog.entity.Comment;
import com.scs.web.blog.util.Result;

import java.util.List;
import java.util.Map;

/**
 * @author zhao
 * @className CommentService
 * @Description TODO
 * @Date 2019/12/5
 * @Version 1.0
 **/
public interface CommentService {
    /**
     * 新评论
     * @param commentDto
     * @return
     */
    Map<String,Object> newcomment(CommentDto commentDto);

    /**
     *
     * @return
     */
    List<Comment> listComment();

    /**
     *
     * @param articleId
     * @return
     */
    Result getComment(long articleId);
//    /**
//     * 分页查询评论信息
//     * @param currentPage
//     * @param count
//     * @return
//     */
//    Result selectByPage(int currentPage,int count);
//    /**
//     * 分页查询评论
//     * @param currentPage
//     * @param pageCount
//     * @return
//     */
//    Result getPageComment(int currentPage,int pageCount);
    /**
     *
     * @param id
     * @return
     */
    Result deleteComment(long id,long articleId);
}