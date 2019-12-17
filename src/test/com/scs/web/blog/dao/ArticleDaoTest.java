package com.scs.web.blog.dao;


import com.scs.web.blog.domain.vo.ArticleVo;
import com.scs.web.blog.entity.Article;
import com.scs.web.blog.factory.DaoFactory;
import com.scs.web.blog.util.JSoupSpider;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;
import java.util.List;

public class ArticleDaoTest {
    private static Logger logger = LoggerFactory.getLogger(ArticleDaoTest.class);
    private ArticleDao articleDao = DaoFactory.getArticleDaoInstance();
    @Test
    public void batchInsert() {
        try {
            int[] result = articleDao.batchInsert(JSoupSpider.getArticle());
            if(result.length !=0){
                logger.info("成功新增"+result.length+"个用户");
            }
        } catch (SQLException e) {
            logger.error("异常");
        }
    }



    @Test
    public void selectByKeywords() throws SQLException{
        List<ArticleVo> articleList = articleDao.selectByKeywords("问");
        System.out.println(articleList.size());
    }


    @Test
    public void insert() throws SQLException{
        Article article = new Article();
        article.setUserId((long) 1);
        article.setTopicId((long) 2);
        article.setTitle("lll");
        article.setContent("oooo");
        article.setCover("ioioio");
        article.setDiamond(3);
        article.setComments(4);
        article.setLikes(3);
        article.setText("kskks");
        int result = articleDao.insert(article);
        if(result == 1){
            logger.info("成功");
        }
    }

    @Test
    public void getArticlesByUserId()throws SQLException {
        List<ArticleVo> articleVoList = articleDao.selectByUserId(1L);
        System.out.println(articleVoList.size());
    }

    @Test
    public void getArticle()throws SQLException {
        Article article = articleDao.getArticle(1);
        if (article != null) {
            System.out.println(article);
        }
    }

    @Test
    public void selectAll()throws SQLException {
        List<Article> articleList = articleDao.selectAll();
        if (articleList.size()>0) {
            System.out.println(articleList.size());
        }
    }

    @Test
    public void update() throws SQLException {
        Article article = articleDao.getArticle(1);
        article.setLikes(article.getLikes()-1);
        articleDao.update(article);
    }
}