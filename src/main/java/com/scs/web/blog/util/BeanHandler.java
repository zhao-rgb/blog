package com.scs.web.blog.util;

import com.scs.web.blog.domain.vo.ArticleVo;
import com.scs.web.blog.entity.Article;
import com.scs.web.blog.entity.Topic;
import com.scs.web.blog.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author zhao
 * @className BeanHandler
 * @Description TODO
 * @Date 2019/11/25
 * @Version 1.0
 **/
public class BeanHandler {
    private static Logger logger = LoggerFactory.getLogger(BeanHandler.class);

//    public  static List<User> convertUser(ResultSet rs) {
//        List<User> userList = new ArrayList<>(50);
//        try {
//            while (rs.next()) {
//                User user = new User();
//                user.setId(rs.getLong("id"));
//                user.setMobile(rs.getString("mobile"));
//                user.setPassword(rs.getString("password"));
//                user.setNickname(rs.getString("nickname"));
//                user.setAvatar(rs.getString("avatar"));
//                user.setGender(rs.getString("gender"));
//                user.setBirthday(rs.getDate("birthday").toLocalDate());
//                user.setIntroduction(rs.getString("introduction"));
//                user.setAddress(rs.getString("address"));
//                user.setFollows(rs.getShort("follows"));
//                user.setFans(rs.getShort("fans"));
//                user.setArticles(rs.getShort("articles"));
//                user.setCreateTime(rs.getTimestamp("create_time").toLocalDateTime());
//                user.setStatus(rs.getShort("status"));
//                userList.add(user);
//            }
//        } catch (SQLException e) {
//            logger.error("查询用户数据产生异常");
//        }
//        return userList;
//    }

    public static List<ArticleVo> convertArticle(ResultSet rs) {
        List<ArticleVo> articleVoList = new ArrayList<>();
        try {
            while (rs.next()) {
                ArticleVo articleVo = new ArticleVo();
                //文章自身信息
                Article article = new Article();
                article.setId(rs.getLong("id"));
                article.setUserId(rs.getLong("user_id"));
                article.setTopicId(rs.getLong("topic_id"));
                article.setTitle(rs.getString("title"));
                article.setContent(rs.getString("content"));
                article.setCover(rs.getString("cover"));
//                article.setDiamond(rs.getLong("diamond"));
                article.setLikes(rs.getInt("likes"));
                article.setComments(rs.getInt("comments"));
                article.setPublishTime(rs.getTimestamp("public_time").toLocalDateTime());

//                //作者信息
//                User author = new User();
//                author.setId(rs.getLong("user_id"));
//                author.setNickname(rs.getString("nickname"));
//                author.setAvatar(rs.getString("avatar"));
//
//                //专题信息
//                Topic topic = new Topic();
//                topic.setId(rs.getLong("topic_id"));
//                topic.setTopicName(rs.getString("topic_name"));
//                topic.setLogo(rs.getString("logo"));
//
//                //给文章视图对象设置三块内容
//                articleVo.setArticle(article);
//                articleVo.setAuthor(author);
//                articleVo.setTopic(topic);
//                //加入列表
                articleVo.setArticle(article);
                articleVoList.add(articleVo);
            }
        } catch (SQLException e) {
            logger.error("文章数据结果集解析异常");
        }
        return articleVoList;
    }
}
