package com.scs.web.blog.dao;

import com.scs.web.blog.domain.dto.UserDto;
import com.scs.web.blog.domain.vo.UserVo;
import com.scs.web.blog.entity.Article;
import com.scs.web.blog.entity.User;
import com.scs.web.blog.factory.DaoFactory;
import com.scs.web.blog.util.JSoupSpider;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.sql.SQLException;
import java.time.Year;
import java.util.ArrayList;
import java.util.List;


public class UserDaoTest {
      private static Logger logger = LoggerFactory.getLogger(UserDaoTest.class);
      private UserDao userDao = DaoFactory.getUserDaoInstance();
    @Test
    public void batchInsert() {
        try {
            int[] result = userDao.batchInsert(JSoupSpider.getUsers());
            if(result.length !=0){
                logger.info("成功新增"+result.length+"个用户");
            }
        } catch (SQLException e) {
            logger.error("批量新增用户出现异常");
        }
    }
    @Test
    public void insert(){
        try {
            UserDto userDto = new UserDto();
            userDto.setMobile("13457513856");
            userDto.setPassword("666666");
            userDto.setNickname("苏玉溪");
            int result = userDao.insert(userDto);
            if (result == 1) {
                logger.info("成功新增一名用户！");
            }
        } catch (SQLException e) {
            logger.error("新增一名用户失败！");
        }
    }
//    @Test
//    public void userById() {
//        try {
//            User user = userDao.getUserById((long) 1);
//            System.out.println(user);
//        } catch (SQLException e) {
//            logger.error("查询指定id用户出错");
//        }
//    }

    @Test
    public void getArticleById() {
        List<Article> articleList = new ArrayList<>();
        try {
            articleList = userDao.getArticleById((long) 1);
        } catch (SQLException e) {
            logger.error("查询某用户发表的文章信息出错");
        }
        articleList.forEach(article -> {
            System.out.println(article);
        });
    }


    @Test
    public void findUserByMobile() throws SQLException {
        User user = userDao.findUserByMobile("18851865555");
        System.out.println(user);
    }


    @Test
    public void selectByKeywords() throws SQLException{
        List<User> userList = userDao.selectByKeywords("二");
        System.out.println(userList.get(0).getNickname());
    }

    @Test
    public void selectHotUsers() throws SQLException {
        List<User> userList = userDao.selectHotUsers();
        userList.forEach(System.out::println);
    }

    @Test
    public void selectByPage() throws SQLException {
        List<User> userList = userDao.selectByPage(1, 10);
        userList.forEach(System.out::println);
    }


    @Test
    public void getUser()throws SQLException {
        UserVo user = userDao.getUser(1L);
        if (user != null) {
            System.out.println(user);
        }
    }

    @Test
    public void updatearticle() throws SQLException{
        int result = userDao.updatearticle(1l);
        System.out.println(result);
    }

    @Test
    public void updatee() throws SQLException{
        User user = userDao.getUserr(1);
        user.setArticles((user.getArticles()));
        userDao.updatee(user);
    }

    @Test
    public void update()throws SQLException {
        User user = userDao.findUserByMobile("18851865555");
        user.setAvatar("111");
        userDao.updateavatar(user);
    }
}

