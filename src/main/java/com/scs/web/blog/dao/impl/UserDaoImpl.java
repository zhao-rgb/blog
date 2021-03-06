package com.scs.web.blog.dao.impl;

import com.scs.web.blog.dao.UserDao;
import com.scs.web.blog.domain.dto.UserDto;
import com.scs.web.blog.domain.vo.ArticleVo;
import com.scs.web.blog.domain.vo.UserVo;
import com.scs.web.blog.entity.Article;
import com.scs.web.blog.entity.User;
import com.scs.web.blog.factory.DaoFactory;
import com.scs.web.blog.util.*;
import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @author zhao
 * @className UserDaoImpl
 * @Description TODO
 * @Date 2019/11/9
 * @Version 1.0
 **/
public class UserDaoImpl implements UserDao {
    private static Logger logger = LoggerFactory.getLogger(UserDaoImpl.class);

    @Override
    public int[] batchInsert(List<User> userList) throws SQLException {
        Connection connection = DbUtil.getConnection();
        String sql = "INSERT INTO t_user (mobile,password,nickname,avatar,gender,birthday,introduction,create_time) VALUES (?,?,?,?,?,?,?,?) ";
        PreparedStatement pst = connection.prepareStatement(sql);
        connection.setAutoCommit(false);
        userList.forEach(user -> {
            try {
                pst.setString(1, user.getMobile());
                pst.setString(2, user.getPassword());
                pst.setString(3, user.getNickname());
                pst.setString(4, user.getAvatar());
                pst.setString(5, user.getGender());
                // 日期的设置，可以使用setObject
                pst.setObject(6, user.getBirthday());
                pst.setString(7, user.getIntroduction());
                pst.setObject(8, user.getCreateTime());
                pst.addBatch();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
        int[] result = pst.executeBatch();
        // 被忘记提交
        connection.commit();
        DbUtil.close(connection, pst);
        return result;

    }

    @Override
    public int insert(UserDto userDto) throws SQLException {
        Connection connection = DbUtil.getConnection();
        String sql = "INSERT INTO t_user(mobile, password, nickname, create_time,birthday) VALUES(?, ?, ?, ?,?) ";
        PreparedStatement pst = connection.prepareStatement(sql);
        pst.setString(1, userDto.getMobile());
        pst.setString(2, DigestUtils.md5Hex(userDto.getPassword()));
        pst.setString(3, userDto.getNickname());
        pst.setObject(4, Timestamp.valueOf(LocalDateTime.now()));
        pst.setObject(5, DataUtil.getBirthday());
        int i = pst.executeUpdate();
        System.out.println("执行为插入方法后受影响的行数为：" + i);
        return i;
    }

    @Override
    public User findUserByMobile(String mobile) throws SQLException {
        Connection connection = DbUtil.getConnection();
        String sql = "SELECT * FROM t_user WHERE mobile = ? ";
        PreparedStatement pst = connection.prepareStatement(sql);
        pst.setString(1, mobile);
        ResultSet rs = pst.executeQuery();
        User user = convertUser(rs).get(0);
        DbUtil.close(connection, pst, rs);
        return user;
    }

    @Override
    public List<User> selectHotUsers() throws SQLException {
        Connection connection = DbUtil.getConnection();
        String sql = "SELECT * FROM t_user ORDER BY fans DESC LIMIT 8 ";
        PreparedStatement pst = connection.prepareStatement(sql);
        ResultSet rs = pst.executeQuery();
//        DbUtil.close(connection, pst, rs);
        return convertUser(rs);
    }

    @Override
    public List<User> selectByPage(int currentPage, int count) throws SQLException {
        Connection connection = DbUtil.getConnection();
        String sql = "SELECT * FROM t_user " +
                "ORDER BY id  LIMIT ?,? ";
        PreparedStatement pst = connection.prepareStatement(sql);
        pst.setInt(1, (currentPage - 1) * count);
        pst.setInt(2, count);
        ResultSet rs = pst.executeQuery();
        List<User> userList = convertUser(rs);
        DbUtil.close(connection, pst, rs);
        return userList;
    }

    @Override
    public UserVo getUser(long id) throws SQLException {
        Connection connection = DbUtil.getConnection();
        String sql = "SELECT * FROM t_user WHERE id = ? ";
        PreparedStatement pst = connection.prepareStatement(sql);
        pst.setLong(1, id);
        ResultSet rs = pst.executeQuery();
        List<User> users= convertUser(rs);
        User user = users.get(0);
        UserVo userVo = new UserVo();
        userVo.setUser(user);
        System.out.println(user.getId());
        List<ArticleVo> articleVoList = DaoFactory.getArticleDaoInstance().selectByUserId(user.getId());
        if (articleVoList != null) {
            userVo.setArticleList(articleVoList);
        }
        DbUtil.close(connection, pst, rs);
        return userVo;
    }

    @Override
    public List<Article> getArticleById(Long id) throws SQLException {
        Connection connection = DbUtil.getConnection();
        String sql = "SELECT * FROM t_article WHERE user_id = ? ";
        PreparedStatement pstmt = connection.prepareStatement(sql);
        pstmt.setLong(1, id);
        ResultSet rs = pstmt.executeQuery();
        List<Article> articleList = new ArrayList<>();
        while (rs.next()) {
            Article article = new Article();
            article.setId(rs.getLong("id"));
            article.setTitle(rs.getString("title"));
            article.setContent(rs.getString("content"));
            article.setCover(rs.getString("cover"));
            article.setDiamond(rs.getInt("diamond"));
            article.setComments(rs.getInt("comments"));
            article.setLikes(rs.getInt("likes"));
            article.setPublishTime(rs.getTimestamp("publish_time").toLocalDateTime());
            article.setText(rs.getString("text"));
            articleList.add(article);
        }
        return articleList;
    }

    @Override
    public List<User> selectByKeywords(String keywords) throws SQLException {
        Connection connection = DbUtil.getConnection();
        String sql = "SELECT * FROM t_user " +
                "WHERE nickname LIKE ?  OR introduction LIKE ? ";
        PreparedStatement pst = connection.prepareStatement(sql);
        pst.setString(1, "%" + keywords + "%");
        pst.setString(2, "%" + keywords + "%");
        ResultSet rs = pst.executeQuery();
        List<User> userList = convertUser(rs);
        DbUtil.close(connection, pst, rs);
        return userList;
    }

    private static List<User> convertUser(ResultSet rs) {
        List<User> userList = new ArrayList<>(50);
        try {
            while (rs.next()) {
                User user = new User();
                user.setId(rs.getLong("id"));
                user.setMobile(rs.getString("mobile"));
                user.setPassword(rs.getString("password"));
                user.setNickname(rs.getString("nickname"));
                user.setAvatar(rs.getString("avatar"));
                user.setGender(rs.getString("gender"));
                user.setBirthday(rs.getDate("birthday").toLocalDate());
                user.setIntroduction(rs.getString("introduction"));
                user.setAddress(rs.getString("address"));
                user.setFollows(rs.getShort("follows"));
                user.setFans(rs.getShort("fans"));
                user.setArticles(rs.getShort("articles"));
                user.setCreateTime(rs.getTimestamp("create_time").toLocalDateTime());
                user.setStatus(rs.getShort("status"));
                userList.add(user);
            }
        } catch (SQLException e) {
            logger.error("查询用户数据产生异常");
        }
        return userList;
    }


    @Override
    public int update(User user) throws SQLException {
        Connection connection = DbUtil.getConnection();
        String sql = "UPDATE t_user SET nickname = ?, gender = ?, birthday = ?, address = ?, introduction = ?, homepage = ? WHERE id = ?";
        PreparedStatement pstmt = connection.prepareStatement(sql);
        pstmt.setString(1, user.getNickname());
        pstmt.setString(2, user.getGender());
        pstmt.setObject(3, user.getBirthday());
        pstmt.setString(4, user.getAddress());
        pstmt.setString(5, user.getIntroduction());
        pstmt.setString(6, user.getHomepage());
        pstmt.setLong(7, user.getId());
        int i =pstmt.executeUpdate();
        System.out.println(i);
        return i;
    }


    @Override
    public int updatearticle(long id) throws SQLException {
        Connection connection = DbUtil.getConnection();
        String sql = "UPDATE t_user SET articles = (SELECT COUNT(user_id)FROM t_article WHERE user_id =?) WHERE id =?";
        PreparedStatement pst = connection.prepareStatement(sql);
//        pst.setLong(1,selectarticle(id) +1);
        pst.setLong(1,id);
        pst.setLong(2,id);
        int result = pst.executeUpdate();
        System.out.println(result);
        return  result;
    }

    @Override
    public User getUserr(long userId) throws SQLException {
        Connection connection = DbUtil.getConnection();
        String sql = "SELECT * FROM t_user WHERE id = "+userId;
        PreparedStatement pst = connection.prepareStatement(sql);
        ResultSet rs = pst.executeQuery(sql);
        User user =null;
        if(rs.next()){
            user = new User();
            user.setId(rs.getLong("id"));
            user.setMobile(rs.getString("mobile"));
            user.setPassword(rs.getString("password"));
            user.setNickname(rs.getString("nickname"));
            user.setAvatar(rs.getString("avatar"));
            user.setGender(rs.getString("gender"));
            user.setBirthday(rs.getDate("birthday").toLocalDate());
            user.setIntroduction(rs.getString("introduction"));
            user.setAddress(rs.getString("address"));
            user.setFollows(rs.getShort("follows"));
            user.setFans(rs.getShort("fans"));
            user.setArticles(rs.getShort("articles"));
            user.setCreateTime(rs.getTimestamp("create_time").toLocalDateTime());
            user.setStatus(rs.getShort("status"));
        }
        return user;
    }

    @Override
    public int updatee(User user) throws SQLException {
        Connection connection = DbUtil.getConnection();
        String sql = "UPDATE  t_user SET articles = ? WHERE id = ? ";
        PreparedStatement pst = connection.prepareStatement(sql);
        pst.setLong(1,user.getArticles());
        pst.setLong(2,user.getId());
        int i = pst.executeUpdate();
        return i;
    }

    @Override
    public void updateavatar(User user) throws SQLException {
        Connection connection = DbUtil.getConnection();
        String sql = "UPDATE t_user SET avatar=? WHERE mobile = ? ";
        PreparedStatement pst = connection.prepareStatement(sql);
        pst.setString(1,user.getAvatar());
        pst.setString(2,user.getMobile());
        pst.executeUpdate();
    }
}
