package com.scs.web.blog.service;

import com.scs.web.blog.entity.Like;
import com.scs.web.blog.util.Result;

/**
 * @author zhao
 * @className LikeService
 * @Description TODO
 * @Date 2019/12/13
 * @Version 1.0
 **/
public interface LikeService {

    /**
     * 喜欢数加一
     * @param
     * @return
     */
    Result addLike(long userId, long articleId);

    /**
     * 取消喜欢
     * @param userId
     * @param articleId
     * @return
     */
    Result lessenLike(long userId, long articleId);
}
