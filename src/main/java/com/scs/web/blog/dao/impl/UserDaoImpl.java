package com.scs.web.blog.dao.impl;

import com.scs.web.blog.dao.UserDao;
import com.scs.web.blog.domain.dto.UserDto;
import com.scs.web.blog.domain.vo.UserVo;
import com.scs.web.blog.entity.Article;
import com.scs.web.blog.entity.User;
import com.scs.web.blog.util.BeanHandler;
import com.scs.web.blog.util.DbUtil;
import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
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
        PreparedStatement pstmt = connection.prepareStatement(sql);
        connection.setAutoCommit(false);
        userList.forEach(user -> {
            try {
                pstmt.setString(1, user.getMobile());
                pstmt.setString(2, user.getPassword());
                pstmt.setString(3, user.getNickname());
                pstmt.setString(4, user.getAvatar());
                pstmt.setString(5, user.getGender());
                // 日期的设置，可以使用setObject
                pstmt.setObject(6, user.getBirthday());
                pstmt.setString(7, user.getIntroduction());
                pstmt.setObject(8, user.getCreateTime());
                pstmt.addBatch();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
        int[] result = pstmt.executeBatch();
        // 被忘记提交
        connection.commit();
//        DbUtil.close(null, pstmt, connection);
        return result;

    }

    @Override
    public int insert(UserDto userDto) throws SQLException {
        Connection connection = DbUtil.getConnection();
        String sql = "INSERT INTO t_user(mobile, password, nickname, create_time) VALUES(?, ?, ?, ?) ";
        PreparedStatement pstmt = connection.prepareStatement(sql);
        pstmt.setString(1, userDto.getMobile());
        pstmt.setString(2, DigestUtils.md5Hex(userDto.getPassword()));
        pstmt.setString(3, userDto.getNickname());
        pstmt.setObject(4, Timestamp.valueOf(LocalDateTime.now()));
        int i = pstmt.executeUpdate();
        System.out.println("执行为插入方法后受影响的行数为：" + i);
        return i;
    }

    @Override
    public User findUserByMobile(String mobile) throws SQLException{
        Connection connection = DbUtil.getConnection();
        String sql = "SELECT * FROM t_user WHERE mobile = ? ";
        PreparedStatement pstmt = connection.prepareStatement(sql);
        pstmt.setString(1,mobile);
        ResultSet rs = pstmt.executeQuery();
        User user = convert(rs).get(0);
//        DbUtil.close(null, pstmt, connection);
        return user;
    }

    @Override
    public List<User> selectHotUsers() throws SQLException {
        Connection connection = DbUtil.getConnection();
        String sql = "SELECT * FROM t_user ORDER BY fans DESC LIMIT 10 ";
        PreparedStatement pst = connection.prepareStatement(sql);
        ResultSet rs = pst.executeQuery();
        return convert(rs);
    }

    @Override
    public List<User> selectByPage(int currentPage, int count) throws SQLException {
        Connection connection = DbUtil.getConnection();
        String sql = "SELECT * FROM t_user " +
                "ORDER BY id  LIMIT ?,? ";
        PreparedStatement pstmt = connection.prepareStatement(sql);
        pstmt.setInt(1, (currentPage - 1) * count);
        pstmt.setInt(2, count);
        ResultSet rs = pstmt.executeQuery();
        List<User> userList = convert(rs);
//        DbUtil.close(null, pstmt, connection);
        return userList;
    }

    @Override
    public User getUserById(Long id) throws SQLException {
        Connection connection = DbUtil.getConnection();
        String sql = "SELECT * FROM t_user WHERE id = ? ";
        PreparedStatement pstmt = connection.prepareStatement(sql);
        pstmt.setLong(1, id);
        ResultSet rs = pstmt.executeQuery();
        User user = new User();
        while (rs.next()) {
            user.setId(rs.getLong("id"));
            user.setMobile(rs.getString("mobile"));
            user.setNickname(rs.getString("nickname"));
            user.setAvatar(rs.getString("avatar"));
            user.setGender(rs.getString("gender"));
            if (rs.getDate("birthday") != null) {
                user.setBirthday(rs.getDate("birthday").toLocalDate());
            } else {
                user.setBirthday(null);
            }
            user.setAddress(rs.getString("address"));
            user.setIntroduction(rs.getString("introduction"));
            user.setHomepage(rs.getString("homepage"));
            user.setFollows(rs.getShort("follows"));
            user.setFans(rs.getShort("fans"));
            user.setArticles(rs.getShort("articles"));
            user.setCreateTime(rs.getTimestamp("create_time").toLocalDateTime());
            user.setAddress(rs.getString("address"));
            user.setStatus(rs.getShort("status"));
        }
        return user;
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
//            article.setText(rs.getString("text"));
            articleList.add(article);
        }
        return articleList;
    }

    @Override
    public List<User> selectByKeywords(String keywords) throws SQLException {
        Connection connection = DbUtil.getConnection();
        String sql = "SELECT * FROM t_user " +
                "WHERE nickname LIKE ?  OR introduction LIKE ? ";
        PreparedStatement pstmt = connection.prepareStatement(sql);
        pstmt.setString(1, "%" + keywords + "%");
        pstmt.setString(2, "%" + keywords + "%");
        ResultSet rs = pstmt.executeQuery();
        List<User> userList = convert(rs);
//        DbUtil.close(null, pstmt, connection);
        return userList;
    }

    private List<User> convert(ResultSet rs) {
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
//                user.setBanner(rs.getString("banner"));
//                user.setEmail(rs.getString("email"));
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

}
