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
public class CommentDto implements Serializable {
    private String nickname;
    private String content;
    private LocalDateTime createTime;
    private String code;

}
