package com.scs.web.blog.domain.vo;

import com.scs.web.blog.entity.Article;
import com.scs.web.blog.entity.Topic;
import com.scs.web.blog.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @author zhao
 * @className ArticleVo
 * @Description TODO
 * @Date 2019/11/15
 * @Version 1.0
 **/
@Data
public class ArticleVo {
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

    private String nickname;
    private String avatar;

    private Article article;
    private User author;
    private Topic topic;

}
