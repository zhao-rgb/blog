package com.scs.web.blog.service.impl;

import com.scs.web.blog.dao.ArticleDao;
import com.scs.web.blog.domain.vo.ArticleVo;
import com.scs.web.blog.entity.Article;
import com.scs.web.blog.factory.DaoFactory;
import com.scs.web.blog.service.ArticleService;
import com.scs.web.blog.util.Message;
import com.scs.web.blog.util.ResultCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.scs.web.blog.util.Result;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zhao
 * @className ArticleServiceImpl
 * @Description TODO
 * @Date 2019/11/15
 * @Version 1.0
 **/
public class ArticleServiceImpl implements ArticleService {
    private ArticleDao articleDao = DaoFactory.getArticleDaoInstance();
    private static Logger logger = LoggerFactory.getLogger(ArticleServiceImpl.class);



    @Override
    public List<Article> initArticle() {
        List<Article> articleList = new ArrayList<>();
        try {
            articleList = articleDao.selectAll();
        } catch (SQLException e) {
            logger.error("初始化图书信息出错");
            e.printStackTrace();
        }
        logger.info("成功初始化图书信息");
        return articleList;
    }

    @Override
    public List<ArticleVo> getHotArticles() {
        List<ArticleVo> articleVoList = new ArrayList<>(20);
        try {
            articleVoList = articleDao.selectHotArticles();
        } catch (SQLException e) {
            logger.error("查询热门文章出现异常");
        }
        return articleVoList;
    }

    @Override
    public Result getArticlesByPage(int currentPage, int count) {
        List<ArticleVo> articleVoList = null;
        try {
            articleVoList = articleDao.selectByPage(currentPage, count);
        } catch (SQLException e) {
            logger.error("分页查询文章出现异常");
        }
        if (articleVoList != null) {
            return Result.success(articleVoList);
        } else {
            return Result.failure(ResultCode.RESULT_CODE_DATA_NONE);
        }
    }

    @Override
    public List<ArticleVo> articleById(Long id) {
        List<ArticleVo> articleVoList = new ArrayList<>(20);
        try {
            articleVoList = articleDao.getArticleById(id);
        } catch (SQLException e) {
            logger.error("获取id=" + id + "的文章出错");
        }
        return articleVoList;
    }

    @Override
    public Result selectByKeywords(String keywords) {
        List<ArticleVo> articleVoList = null;
        try {
            articleVoList = articleDao.selectByKeywords(keywords);
        } catch (SQLException e) {
            logger.error("根据关键字查询文章出现异常");
        }
        if (articleVoList != null) {
            return Result.success(articleVoList);
        } else {
            return Result.failure(ResultCode.RESULT_CODE_DATA_NONE);
        }
    }

    @Override
    public Map<String, Object> newarticle(Article article) {
        Map<String , Object> map = new HashMap<>();
        int i = 0;
        try {
            i = articleDao.insert(article);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("i的值：" + i);
        if(i == 1){
            map.put("msg", Message.REGISTER_SUCCESS);
            map.put("data",article);
            logger.info("图书新增成功");
        }else {
            map.put("msg",Message.REGISTER_DEFEATED);
        }
        return map;
    }


}
