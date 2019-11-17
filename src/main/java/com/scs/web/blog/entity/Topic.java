package com.scs.web.blog.entity;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author zhao
 * @className Topic
 * @Description TODO
 * @Date 2019/11/15
 * @Version 1.0
 **/
@Data
public class Topic {
    private Long id;
    private String name;
    private String logo;
    private Integer articles;
    private Integer follows;
    private LocalDateTime createTime;
}
