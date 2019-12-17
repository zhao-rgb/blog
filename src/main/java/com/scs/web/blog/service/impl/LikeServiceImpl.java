package com.scs.web.blog.service.impl;

import com.scs.web.blog.dao.ArticleDao;
import com.scs.web.blog.dao.LikeDao;
import com.scs.web.blog.domain.vo.ArticleVo;
import com.scs.web.blog.entity.Article;
import com.scs.web.blog.entity.Like;
import com.scs.web.blog.factory.DaoFactory;
import com.scs.web.blog.service.LikeService;
import com.scs.web.blog.util.Result;
import com.scs.web.blog.util.ResultCode;

import java.sql.SQLException;
import java.util.List;

/**
 * @author zhao
 * @className LikeServiceImpl
 * @Description TODO
 * @Date 2019/12/13
 * @Version 1.0
 **/
public class LikeServiceImpl implements LikeService {
    private LikeDao likeDao = DaoFactory.getLikeDaoInstance();
    private ArticleDao articleDao = DaoFactory.getArticleDaoInstance();

    @Override
    public Result addLike(long userId, long articleId) {
        boolean flag;
        try {
            flag = likeDao.insertLike(userId, articleId);
            if(flag){
                Article article = articleDao.getArticle(articleId);
                if (article != null) {
                    article.setLikes(article.getLikes()+1);
                    articleDao.update(article);
                }
            } else {
                return Result.failure(ResultCode.LIKES_NOT_FOUND);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return Result.failure(ResultCode.DATABASE_ERROR);
        }
        return Result.success();
    }

    @Override
    public Result lessenLike(long userId, long articleId) {
        boolean flag;
        try {
            flag = likeDao.deleteLike(userId, articleId);
            if(flag){
                Article article = articleDao.getArticle(articleId);
                if (article != null) {
                    article.setLikes(article.getLikes()-1);
                    articleDao.update(article);
                }
            } else {
                return Result.failure(ResultCode.LIKES_NOT_FOUND);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return Result.failure(ResultCode.DATABASE_ERROR);
        }
        return Result.success();
    }
}
