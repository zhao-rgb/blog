package com.scs.web.blog.service.impl;

import com.scs.web.blog.dao.UserDao;
import com.scs.web.blog.domain.dto.UserDto;
import com.scs.web.blog.domain.vo.UserVo;
import com.scs.web.blog.entity.Article;
import com.scs.web.blog.entity.User;
import com.scs.web.blog.factory.DaoFactory;
import com.scs.web.blog.service.UserService;
import com.scs.web.blog.util.Message;
import com.scs.web.blog.util.Result;
import com.scs.web.blog.util.ResultCode;
import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zhao
 * @className UserServiceImpl
 * @Description TODO
 * @Date 2019/11/9
 * @Version 1.0
 **/
public class UserServiceImpl implements UserService {
    private UserDao userDao = DaoFactory.getUserDaoInstance();
    private static Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
    @Override
    public Map<String, Object> signIn(UserDto userDto){
        User user = null;
        Map<String,Object> map = new HashMap<>();
        try {
            user = userDao.findUserByMobile(userDto.getMobile());
        } catch (SQLException e) {
            logger.error("根据手机号查询用户出现异常");
        }
        if(user !=null){
            if(user.getPassword().equals(DigestUtils.md5Hex(userDto.getPassword()))){
                map.put("msg","登录成功");
                map.put("data",user);
            }else {
                map.put("msg","密码错误");
            }
        }else {
            map.put("msg","手机号不存在");
        }
        return map;
    }

    @Override
    public Map<String, Object> register(UserDto userDto) {
        Map<String, Object> map = new HashMap<>();
        int i = 0;
        try {
            i = userDao.insert(userDto);
        } catch (SQLException e) {
            e.printStackTrace();
            logger.error("用户注册时出错！");
        }
        System.out.println("i的值为：" + i);
        if (i == 1) {
            map.put("msg", Message.REGISTER_SUCCESS);
            map.put("data", userDto);
            logger.info("注册" + userDto.getMobile() + "用户成功");
        } else {
            map.put("msg", Message.REGISTER_DEFEATED);
        }
        return map;
    }


    @Override
    public Result getHotUsers() {
        List<User> userList = null;
        try {
            userList = userDao.selectHotUsers();
        } catch (SQLException e) {
//            logger.error("获取热门用户出现异常");
            e.printStackTrace();
        }
        if (userList != null) {
            //成功并返回数据
            return Result.success(userList);
        }
        else {
            return Result.failure(ResultCode.RESULT_CODE_DATA_NONE);
        }
    }


    @Override
    public Result selectByPage(int currentPage, int count) {
        List<User> userList = null;
        try {
            userList = userDao.selectByPage(currentPage, count);
        } catch (
                SQLException e) {
            logger.error("分页查询用户出现异常");
        }
        if (userList != null) {
            return Result.success(userList);
        } else {
            return Result.failure(ResultCode.RESULT_CODE_DATA_NONE);
        }
    }


    @Override
    public List<Object> userById(Long id) {
        User user = null;
            List<Article> articleList = new ArrayList<>();
            List<Object> list = new ArrayList<>();
            try {
            user = userDao.getUserById(id);
            articleList = userDao.getArticleById(id);
            list.add(user);
            list.add(articleList);
            if (user != null && articleList.size() > 0) {
                logger.info("成功获取id=" + id + "的文章信息");
            }
        } catch (SQLException e) {
            logger.error("获取id=" + id + "的文章信息出错");
        }
        return list;
    }

    @Override
    public Result selectByKeywords(String keywords) {
        List<User> userList = null;
        try {
            userList = userDao.selectByKeywords(keywords);
        } catch (SQLException e) {
            logger.error("根据关键字查询用户出现异常");
        }
        if (userList != null) {
            return Result.success(userList);
        } else {
            return Result.failure(ResultCode.RESULT_CODE_DATA_NONE);
        }
    }
}
