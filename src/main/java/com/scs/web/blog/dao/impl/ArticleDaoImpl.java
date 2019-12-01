package com.scs.web.blog.dao.impl;


import com.scs.web.blog.dao.ArticleDao;
import com.scs.web.blog.domain.vo.ArticleVo;
import com.scs.web.blog.entity.Article;
import com.scs.web.blog.util.BeanHandler;
import com.scs.web.blog.util.DataUtil;

import com.scs.web.blog.util.DbUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author zhao
 * @className ArticleDaoImpl
 * @Description TODO
 * @Date 2019/11/10
 * @Version 1.0
 **/
public class ArticleDaoImpl implements ArticleDao {
    private static Logger logger = LoggerFactory.getLogger(ArticleDaoImpl.class);

    @Override
    public int[] batchInsert(List<Article> articleList) throws SQLException {
        Connection connection = DbUtil.getConnection();
        String sql = "INSERT INTO t_article (user_id,topic_id,title,content,cover,diamond,comments,likes,publish_time,text) VALUES(?,?,?,?,?,?,?,?,?,?) ";
        PreparedStatement pstmt = connection.prepareStatement(sql);
        connection.setAutoCommit(false);
        articleList.forEach(article -> {
            try {
                pstmt.setInt(1, DataUtil.getUserId());
                pstmt.setInt(2,DataUtil.getTopicId());
                pstmt.setString(3, article.getTitle());
                pstmt.setString(4, article.getContent());
                pstmt.setString(5, article.getCover());
                pstmt.setInt(6, article.getDiamond());
                pstmt.setInt(7, article.getComments());
                pstmt.setInt(8, article.getLikes());
                pstmt.setObject(9, article.getPublishTime());
                pstmt.setString(10,article.getText());
                pstmt.addBatch();
            } catch (SQLException e) {
                logger.error("批量导入文章信出错");
            }
        });
        int[] result = pstmt.executeBatch();
        connection.commit();

        return result;
    }


    @Override
    public List<ArticleVo> selectHotArticles() throws SQLException {
        List<ArticleVo> articleVoList = new ArrayList<>(20);
        Connection connection = DbUtil.getConnection();
        //在文章表和用户表联查，得到结视图对象
        String sql = "SELECT a.id,a.user_id,a.title,a.content,a.diamond,a.comments,a.likes,b.id,b.nickname,b.avatar\n" +
                "FROM t_article a\n" +
                "LEFT JOIN t_user b\n" +
                "ON a.user_id = b.id\n" +
                "ORDER BY a.comments DESC LIMIT 20";
        PreparedStatement pstmt = connection.prepareStatement(sql);
        ResultSet rs = pstmt.executeQuery();
        while (rs.next()) {
            ArticleVo article = new ArticleVo();
            article.setId(rs.getLong("id"));
            article.setUserId(rs.getLong("user_id"));
            article.setTitle(rs.getString("title"));
            article.setContent(rs.getString("content"));
            article.setDiamond(rs.getString("diamond"));
            article.setNickname(rs.getString("nickname"));
            article.setAvatar(rs.getString("avatar"));
            article.setLikes(rs.getInt("likes"));
            article.setComments(rs.getInt("comments"));
            articleVoList.add(article);
        }
        return articleVoList;
    }

    @Override
    public List<ArticleVo> selectByPage(int currentPage, int count) throws SQLException {
        Connection connection = DbUtil.getConnection();
        String sql = "SELECT a.*,b.topic_name,b.logo,c.nickname,c.avatar " +
                "FROM t_article a " +
                "LEFT JOIN t_topic b " +
                "ON a.topic_id = b.id " +
                "LEFT JOIN t_user c " +
                "ON a.user_id = c.id " +
                "ORDER BY a.id  LIMIT ?,? ";
        PreparedStatement pstmt = connection.prepareStatement(sql);
        pstmt.setInt(1, (currentPage - 1) * count);
        pstmt.setInt(2, count);
        ResultSet rs = pstmt.executeQuery();
        List<ArticleVo> articleVos = convertArticle(rs);
        return articleVos;
    }


    @Override
    public List<ArticleVo> selectByKeywords(String keywords) throws SQLException {
        Connection connection = DbUtil.getConnection();
        //从文章、专题、用户表联查出前端需要展示的数据
        String sql = "SELECT a.*,b.topic_name,b.logo,c.nickname,c.avatar " +
                "FROM t_article a " +
                "LEFT JOIN t_topic b " +
                "ON a.topic_id = b.id " +
                "LEFT JOIN t_user c " +
                "ON a.user_id = c.id " +
                "WHERE a.title LIKE ?  OR a.content LIKE ? ";
        PreparedStatement pst = connection.prepareStatement(sql);
        pst.setString(1, "%" + keywords + "%");
        pst.setString(2, "%" + keywords + "%");
        ResultSet rs = pst.executeQuery();
        List<ArticleVo> articleVos = convertArticle(rs);
        DbUtil.close(connection, pst,rs);
        return articleVos;
    }

    @Override
    public List<ArticleVo> selectByTopicId(long topicId) throws SQLException {
        Connection connection = DbUtil.getConnection();
        //从文章、专题、用户表联查出前端需要展示的数据
        String sql = "SELECT a.*,b.topic_name,b.logo,c.nickname,c.avatar " +
                "FROM t_article a " +
                "LEFT JOIN t_topic b " +
                "ON a.topic_id = b.id " +
                "LEFT JOIN t_user c " +
                "ON a.user_id = c.id " +
                "WHERE a.topic_id = ? ";
        PreparedStatement pstmt = connection.prepareStatement(sql);
        pstmt.setLong(1, topicId);
        ResultSet rs = pstmt.executeQuery();
        List<ArticleVo> articleVos = convertArticle(rs);
//        DbUtil.close(null, pstmt, connection);
        return articleVos;
    }

    @Override
    public List<ArticleVo> getArticleById(Long id) throws SQLException {
        Connection connection = DbUtil.getConnection();
        String sql = "SELECT a.*,b.topic_name,b.logo,c.nickname,c.avatar " +
                "FROM t_article a " +
                "LEFT JOIN t_topic b " +
                "ON a.topic_id = b.id " +
                "LEFT JOIN t_user c " +
                "ON a.user_id = c.id " +
                "WHERE a.id = ?  ";
        PreparedStatement pstmt = connection.prepareStatement(sql);
        pstmt.setLong(1, id);
        ResultSet rs = pstmt.executeQuery();
        List<ArticleVo> articleVo = convertArticle(rs);
        return articleVo;
    }

    public static List<ArticleVo> convertArticle(ResultSet rs) {
        List<ArticleVo> articleVoList = new ArrayList<>();
        try {
            while (rs.next()) {
                ArticleVo articleVo = new ArticleVo();
                articleVo.setId(rs.getLong("id"));
                articleVo.setUserId(rs.getLong("user_id"));
                articleVo.setTopicId(rs.getLong("topic_id"));
                articleVo.setTitle(rs.getString("title"));
                articleVo.setContent(rs.getString("content"));
                articleVo.setCover(rs.getString("cover"));
                articleVo.setDiamond(rs.getString("diamond"));
                articleVo.setComments(rs.getInt("comments"));
                articleVo.setLikes(rs.getInt("likes"));
                articleVo.setPublishTime(rs.getTimestamp("publish_time").toLocalDateTime());
                articleVo.setText(rs.getString("text"));



                articleVo.setNickname(rs.getString("nickname"));
                articleVo.setAvatar(rs.getString("avatar"));
                articleVoList.add(articleVo);
            }
        } catch (SQLException e) {
            logger.error("文章数据结果集解析异常");
        }
        return articleVoList;
    }

    @Override
    public List<Article> selectAll() throws SQLException {
        List<Article> articleList = new ArrayList<>();
        Connection connection = DbUtil.getConnection();
        String sql = "SELECT * FROM t_article ORDER BY id DESC ";
        Statement stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery(sql);
        while (rs.next()) {
            Article article = new Article();
            article.setId(rs.getLong("id"));
            article.setUserId(rs.getLong("user_id"));
//            article.setTopicId(rs.getLong("topic_id"));
            article.setTitle(rs.getString("title"));
            article.setContent(rs.getString("content"));
            article.setCover(rs.getString("cover"));
            article.setDiamond(rs.getInt("diamond"));
            article.setComments(rs.getInt("comments"));
            article.setLikes(rs.getInt("likes"));
            article.setPublishTime(rs.getTimestamp("publish_time").toLocalDateTime());
//            article.setText(rs.getString("text"));
            articleList.add(article);
        }
//        DbUtil.close(rs, stmt, connection);
        return articleList;
    }

}





