package com.scs.web.blog.domain.dto;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author zhao
 * @className CommentDto
 * @Description TODO
 * @Date 2019/12/5
 * @Version 1.0
 **/
@Data
public class CommentDto {
    private Long Id;
    private Long userId;
    private Long articleId;
    private String content;
    private LocalDateTime createTime;
    private String code;

}
