package com.scs.web.blog.service.impl;

import com.scs.web.blog.dao.ArticleDao;
import com.scs.web.blog.dao.LikeDao;
import com.scs.web.blog.domain.vo.ArticleVo;
import com.scs.web.blog.domain.vo.LikeVo;
import com.scs.web.blog.entity.Article;
import com.scs.web.blog.entity.Like;
import com.scs.web.blog.factory.DaoFactory;
import com.scs.web.blog.service.LikeService;
import com.scs.web.blog.util.Result;
import com.scs.web.blog.util.ResultCode;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author zhao
 * @className LikeServiceImpl
 * @Description TODO
 * @Date 2019/12/13
 * @Version 1.0
 **/
@Slf4j
public class LikeServiceImpl implements LikeService {
    private LikeDao likeDao = DaoFactory.getLikeDaoInstance();
    private ArticleDao articleDao = DaoFactory.getArticleDaoInstance();
    private static Logger logger = LoggerFactory.getLogger(LikeServiceImpl.class);

    @Override
    public Result addLike(long userId, long articleId) {
        boolean flag;
        try {
            Like like = likeDao.getLike(userId);
            if(like.getUserId()==userId&&like.getArticleId()==articleId){
                return Result.failure(ResultCode.LIKES_NOT_FOUND);
            }else {
                flag = likeDao.insertLike(userId, articleId);
                if(flag){
                    Article article = articleDao.getArticle(articleId);
                    article.setLikes(article.getLikes()+1);
                    articleDao.update(article);
                }
            }
        } catch (SQLException e) {
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
            return Result.failure(ResultCode.DATABASE_ERROR);
        }
        return Result.success();
    }

    @Override
    public Result getLikes(long userId) {
        List<LikeVo> likeVoList = new ArrayList<>();
        try {
            likeVoList = likeDao.getLikes(userId);
        }catch (SQLException e){
            logger.error("异常");
        }
        if(likeVoList != null){
            return Result.success(likeVoList);
        }else {
            return Result.failure(ResultCode.RESULT_CODE_DATA_NONE);
        }
    }
}
