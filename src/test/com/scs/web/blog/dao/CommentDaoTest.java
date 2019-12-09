package com.scs.web.blog.dao;

import com.scs.web.blog.domain.dto.CommentDto;
import com.scs.web.blog.domain.dto.UserDto;
import com.scs.web.blog.factory.DaoFactory;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;

public class CommentDaoTest {
 private  static Logger logger = LoggerFactory.getLogger(CommentDaoTest.class);
 private CommentDao commentDao = DaoFactory.getCommentDaoInstance();
    @Test
    public void insert() throws SQLException {
        CommentDto commentDto = new CommentDto();
        commentDto.setContent("12252522");
        int result = commentDao.insert(commentDto);
        if(result == 1) {
            logger.info("成功");
        }else {
            logger.error("失败");
        }
    }
}