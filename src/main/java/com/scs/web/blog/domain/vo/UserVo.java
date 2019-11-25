package com.scs.web.blog.domain.vo;

import com.scs.web.blog.domain.dto.SimpleUser;
import com.scs.web.blog.entity.Article;
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

//    private Long id;
//    private String mobile;
//    private String password;
//    private String nickname;
//    private String avatar;
//    private String gender;
//    private LocalDate birthday;
//    private String address;
//    private String introduction;
//    private String banner;
//    private String email;
//    private String homepage;
//    private Short follows;
//    private Short fans;
//    private Short articles;
//    private LocalDateTime createTime;
//    private Short status;
//    private List<Article> articleList;
//    private List<SimpleUser> simpleUserList;
}
