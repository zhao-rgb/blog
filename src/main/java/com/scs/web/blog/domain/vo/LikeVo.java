package com.scs.web.blog.domain.vo;

import com.scs.web.blog.entity.Article;
import com.scs.web.blog.entity.Like;
import lombok.Data;

/**
 * @author zhao
 * @className likeVo
 * @Description TODO
 * @Date 2019/12/22
 * @Version 1.0
 **/
@Data
public class LikeVo {
    private Article article;
    private Like like;
}
