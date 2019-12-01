package com.scs.web.blog.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @author zhao
 * @className Article
 * @Description TODO
 * @Date 2019/11/10
 * @Version 1.0
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Article {
    private Long id;
    private Long userId;
    private Long topicId;
    private String title;
    private String content;
    private String cover;
    private Integer diamond;
    private Integer comments;
    private Integer likes;
    private LocalDateTime publishTime;
    private String text;
}
