package com.scs.web.blog.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @author zhao
 * @className Topic
 * @Description TODO
 * @Date 2019/11/15
 * @Version 1.0
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Topic {
    private Long id;
    //管理员id
    private Long adminId;
    private String topicName;
    private String logo;
    private String description;
    private Integer articles;
    private Integer follows;
    private LocalDateTime createTime;
}
