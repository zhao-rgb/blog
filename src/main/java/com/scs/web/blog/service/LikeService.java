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
     * @param like
     * @return
     */
    Result addLike(Like like);
}
