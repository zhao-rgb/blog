package com.scs.web.blog.service;

import com.scs.web.blog.domain.dto.UserDto;
import com.scs.web.blog.entity.User;
import com.scs.web.blog.factory.ServiceFactory;
import com.scs.web.blog.util.Result;
import org.junit.Test;
import org.junit.runners.Suite;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class UserServiceTest {
    private UserService userService = ServiceFactory.getUserServiceInstance();
    @Test
    public void signIn() {
        UserDto userDao = new UserDto("13991045225","7549c9ceeb760cbbe96a0c2a0554bbac");
        Map<String,Object> map = userService.signIn(userDao);
        map = userService.signIn(userDao);
        System.out.println(map.get("msg"));
        System.out.println(map.get("data"));
    }

//    @Test
//    public void userById() {
//        List<Object> list = userService.userById(23l);
//        System.out.println(list.size());
//    }

    @Test
    public void selectByKeywords() throws SQLException  {
        Result rs = userService.selectByKeywords("二");
        System.out.println(rs.getData());
    }

    @Test
    public void getUser() {
        Result rs = userService.getUser(1);
        System.out.println(rs.getData());
    }
}