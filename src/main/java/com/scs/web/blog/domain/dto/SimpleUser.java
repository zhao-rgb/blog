package com.scs.web.blog.domain.dto;

import lombok.Data;

/**
 * @author zhao
 * @className SimpleUser
 * @Description TODO
 * @Date 2019/11/19
 * @Version 1.0
 **/
@Data
public class SimpleUser {
    private Long id;
    private String nickname;
    private String avatar;
}
