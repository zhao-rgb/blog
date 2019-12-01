package com.scs.web.blog.service;


import com.scs.web.blog.domain.dto.UserDto;
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
     * 获取指定id的用户信息
     * @param id 指定用户id
     * @return 返回该用户信息 及 发表的文章信息
     */
    List<Object> userById(Long id);

    /**
     * 根据昵称或简介模糊搜索用户
     *
     * @param keywords
     * @return Result
     */
    Result selectByKeywords(String keywords);
}
