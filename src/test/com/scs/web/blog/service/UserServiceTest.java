package com.scs.web.blog.service;

import com.scs.web.blog.domain.dto.UserDto;
import com.scs.web.blog.entity.User;
import com.scs.web.blog.factory.ServiceFactory;
import com.scs.web.blog.util.Result;
import org.junit.Test;
import org.junit.runners.Suite;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
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
        Result rs = userService.getUser(1L);
        System.out.println(rs.getData());
    }

    @Test
    public void updateUser() throws SQLException {
        User user = new User();
        user.setNickname("张三");
        user.setGender("男");
        user.setBirthday(LocalDate.now());
        user.setAddress("中古江苏省南京市");
        user.setIntroduction("开开心心过大年");
        user.setHomepage("https://blog.csdn.net/qq_38225558/article/details/82842426");
        user.setId(84L);
        userService.updateUser(user);
    }


    @Test
    public void update(){
        User user = new User();
        user.setMobile("18851856258");
        user.setAvatar("232.jpg");
        Result result = userService.update(user);
        System.out.println(result);
    }

}