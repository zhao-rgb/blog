package com.scs.web.blog.service;

import com.scs.web.blog.domain.vo.ArticleVo;
import com.scs.web.blog.entity.Article;
import com.scs.web.blog.util.Result;

import java.util.List;

/**
 * @author zhao
 * @className ArticleService
 * @Description TODO
 * @Date 2019/11/15
 * @Version 1.0
 **/
public interface ArticleService {
    /**
     * 获取热门文章
     * @return
     */
    List<ArticleVo> getHotArticles();

    /**
     * 获取分页文章
     *
     * @param currentPage,pageCount
     * @return
     */
    Result getArticlesByPage(int currentPage, int count);

    /**
     * 获取文章详情
     *
     * @param id
     * @return
     */
    Result getArticle(long id);


    /**
     * 根据标题或摘要模糊查询文章
     *
     * @param keywords
     * @return
     */
    Result selectByKeywords(String keywords);
}
