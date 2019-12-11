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
}
