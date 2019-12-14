package com.scs.web.blog.entity;

import lombok.Data;

/**
 * @author zhao
 * @className Like
 * @Description TODO
 * @Date 2019/12/13
 * @Version 1.0
 **/
@Data
public class Like {
    private long id;
    private long userId;
    private long articleId;
}
