package com.scs.web.blog.service;

import com.scs.web.blog.domain.dto.CommentDto;

import javax.xml.stream.events.Comment;
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
}
