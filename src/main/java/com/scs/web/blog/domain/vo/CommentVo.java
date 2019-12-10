package com.scs.web.blog.domain.vo;

import com.scs.web.blog.entity.Comment;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author zhao
 * @className CommentVo
 * @Description TODO
 * @Date 2019/12/10
 * @Version 1.0
 **/
@Data
public class CommentVo {
    private Long id;
    private String nickname;
    private Long userId;
    private Long articleId;
    private String content;
    private LocalDateTime createTime;
    private Comment comment;
}
