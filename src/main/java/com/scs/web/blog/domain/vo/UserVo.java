package com.scs.web.blog.domain.vo;

import com.scs.web.blog.domain.dto.SimpleUser;
import com.scs.web.blog.entity.Article;
import com.scs.web.blog.entity.Topic;
import com.scs.web.blog.entity.User;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author zhao
 * @className UserVo
 * @Description TODO
 * @Date 2019/11/19
 * @Version 1.0
 **/
@Data
public class UserVo {
    private User user;
    private List<Article> articles;
    private List<SimpleUser> simpleUsers;
//    private List<Topic> topicList;
//    private List<User> fansList;

}
