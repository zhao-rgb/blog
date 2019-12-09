package com.scs.web.blog.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author zhao
 * @className 关注专题实体类
 * @Description TODO
 * @Date 2019/12/5
 * @Version 1.0
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TopicFollow {
    private  Long id;
    private  Long userId;
    private  Long topicId;
}
