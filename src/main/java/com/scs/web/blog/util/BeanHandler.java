package com.scs.web.blog.util;

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
}
