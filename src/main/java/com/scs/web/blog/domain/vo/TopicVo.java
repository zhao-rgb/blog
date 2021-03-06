package com.scs.web.blog.domain.vo;

import com.scs.web.blog.domain.dto.SimpleUser;
import com.scs.web.blog.entity.Topic;
import com.scs.web.blog.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author zhao
 * @className TopicVo
 * @Description TODO
 * @Date 2019/11/17
 * @Version 1.0
 **/
@Data
public class TopicVo {
    private Long id;
    private Long adminId;
    private String topicName;
    private String logo;
    private String description;
    private Integer articles;
    private Integer follows;
    private LocalDateTime createTime;

    private List<ArticleVo> articleList;

    private SimpleUser simpleUser;
    private List<SimpleUser> simpleUsers;

    private Topic topic;
    private User admin;

    private List<User> followList;
}
