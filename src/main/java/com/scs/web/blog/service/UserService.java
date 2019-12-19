package com.scs.web.blog.service;


import com.scs.web.blog.domain.dto.UserDto;
import com.scs.web.blog.entity.User;
import com.scs.web.blog.util.Result;

import java.util.List;
import java.util.Map;

/**
 * @author zhao
 * @className UserService
 * @Description TODO
 * @Date 2019/11/9
 * @Version 1.0
 **/
public interface UserService {
    /**
     * 用户登录功能
     * @param userDto
     * @return
     */
    Map<String,Object> signIn(UserDto userDto);

    /**
     * 注册新用户
     * @param userDto
     * @return
     */
    Map<String,Object> register(UserDto userDto);

    /**
     * 获取热门用户信息
     * @return
     */
    Result getHotUsers();
    /**
     * 获取分页用户信息
     * @return
     */

    Result selectByPage(int currentPage,int count);

    /**
     * 根据id查询用户详情数据
     * @param id
     * @return Result
     */
    Result getUser(long id);

    /**
     * 根据昵称或简介模糊搜索用户
     *
     * @param keywords
     * @return Result
     */
    Result selectByKeywords(String keywords);

    /**
     * 修改用户信息
     * @param user
     * @return
     */
    Result updateUser(User user);


    /**
     * 修改头像
     * @param user
     * @return
     */
    Result update(User user);
}
