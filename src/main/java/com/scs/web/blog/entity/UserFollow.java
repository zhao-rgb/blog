package com.scs.web.blog.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author zhao
 * @className 关注用户实体
 * @Description TODO
 * @Date 2019/12/5
 * @Version 1.0
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserFollow {
    private Long id;
    private Long fromId;
    private Long toId;
}
