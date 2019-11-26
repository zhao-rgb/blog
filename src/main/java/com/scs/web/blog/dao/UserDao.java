package com.scs.web.blog.dao;

import com.scs.web.blog.domain.dto.UserDto;
import com.scs.web.blog.domain.vo.UserVo;
import com.scs.web.blog.entity.Article;
import com.scs.web.blog.entity.User;
import java.sql.SQLException;
import java.util.List;

/**
 * @author zhao
 * @className UserDao
 * @Description TODO
 * @Date 2019/11/9
 * @Version 1.0
 **/
public interface UserDao {
    /**
     * 批量插入用户信息
     * @param userList
     * @return
     * @throws SQLException
     */
    int[] batchInsert(List<User> userList) throws SQLException;

    /**
     * 新曾用户
     * @param userDto
     * @return
     * @throws SQLException
     */
    int insert(UserDto userDto) throws SQLException;

    /**
     * 根据手机号查找用户
     * @param mobile
     * @return
     * @throws SQLException
     */
   User findUserByMobile(String mobile) throws SQLException;

    /**
     * 查询热门用户
     *
     * @return
     * @throws SQLException
     */
    List<User> selectHotUsers() throws SQLException;

    /**
     * 查询分页用户
     *
     * @return
     * @throws SQLException
     */
    List<User> selectByPage(int currentPage, int count) throws SQLException;

    /**
     * 根据id查询用户详情，包括其他数据
     *
     * @return
     * @throws SQLException
     */
    User getUserById(Long id) throws SQLException;

    /**
     * 通过指定id查找用户发表的文章信息
     * @param id 指定用户id
     * @return
     * @throws SQLException
     */
    List<Article> getArticleById(Long id) throws SQLException;


    /**
     * 模糊搜索用户
     * @param keywords
     * @return
     * @throws SQLException
     */
    List<User> selectByKeywords(String keywords) throws SQLException;

}
