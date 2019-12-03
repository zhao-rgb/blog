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


}