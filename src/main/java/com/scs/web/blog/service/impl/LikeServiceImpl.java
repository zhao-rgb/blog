package com.scs.web.blog.service.impl;

import com.scs.web.blog.dao.LikeDao;
import com.scs.web.blog.entity.Like;
import com.scs.web.blog.factory.DaoFactory;
import com.scs.web.blog.service.LikeService;
import com.scs.web.blog.util.Result;
import com.scs.web.blog.util.ResultCode;

import java.sql.SQLException;

/**
 * @author zhao
 * @className LikeServiceImpl
 * @Description TODO
 * @Date 2019/12/13
 * @Version 1.0
 **/
public class LikeServiceImpl implements LikeService {
    private LikeDao likeDao = DaoFactory.getLikeDaoInstance();



    @Override
    public Result addLike(Like like) {
        boolean a = false;
        boolean b = false;
        try {
            a = likeDao.insertLike(like);
            b = likeDao.addLikes(like.getArticleId());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (a && b) {
            return Result.success();
        } else {
            return Result.failure(ResultCode.RESULT_CODE_DATA_NONE);
        }
    }
}
