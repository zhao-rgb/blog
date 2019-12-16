package com.scs.web.blog.dao;

import com.scs.web.blog.domain.vo.ArticleVo;
import com.scs.web.blog.entity.Article;

import java.sql.SQLException;
import java.util.List;

/**
 * @author zhao
 * @className ArticleDao
 * @Description TODO
 * @Date 2019/11/10
 * @Version 1.0
 **/
public interface ArticleDao {

  /**
   * 初始化文章信息
   * @return
   * @throws SQLException
   */
  List<Article> selectAll() throws SQLException;
    /**
     * 批量新增文章
     * @param articleList
     * @return
     * @throws SQLException
     */
  int [] batchInsert(List<Article> articleList)throws SQLException;

    /**
     * 查询热门文章，返回视图集合
     * @return
     * @throws SQLException
     */
    List<ArticleVo> selectHotArticles() throws SQLException;

  /**
   * 分页获得文章数据
   *
   * @param currentPage
   * @return
   * @throws SQLException
   */
  List<ArticleVo> selectByPage(int currentPage, int count) throws SQLException;


  /**
   * 根据关键字模糊查询所有文章
   *
   * @param keywords
   * @return
   * @throws SQLException
   */
  List<ArticleVo> selectByKeywords(String keywords) throws SQLException;


  /**
   * 根据专题id查询所有文章
   * @param topicId
   * @return
   * @throws SQLException
   */
  List<ArticleVo> selectByTopicId(long topicId) throws SQLException;

  /**
   * 通过指定id查找文章详细信息
   * @param id 指定文章id
   * @return
   * @throws SQLException
   */
  List<ArticleVo> getArticleById(Long id) throws SQLException;

  /**
   * 根据作者id查询所有文章
   * @param userId
   * @return
   * @throws SQLException
   */
  List<ArticleVo> selectByUserId(long userId) throws SQLException;


  /**
   *
   * @param article
   * @return
   * @throws SQLException
   */
  int insert(Article article) throws  SQLException;

  /**
   *
   * @param id
   * @return
   * @throws SQLException
   */
  int delete(long id) throws  SQLException;
}
