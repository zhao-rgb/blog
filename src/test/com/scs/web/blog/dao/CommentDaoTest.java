package com.scs.web.blog.dao;

import com.scs.web.blog.domain.dto.CommentDto;
import com.scs.web.blog.domain.vo.CommentVo;
import com.scs.web.blog.entity.Comment;
import com.scs.web.blog.factory.DaoFactory;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;
import java.util.List;

public class CommentDaoTest {
 private  static Logger logger = LoggerFactory.getLogger(CommentDaoTest.class);
 private CommentDao commentDao = DaoFactory.getCommentDaoInstance();
    @Test
    public void insert() throws SQLException {
        CommentDto commentDto = new CommentDto();


        commentDto.setUserId((long) 6);
        commentDto.setArticleId((long) 4);
        commentDto.setContent("12252522");
        int result = commentDao.insert(commentDto);
        if(result == 1) {
            logger.info("成功");
        }else {
            logger.error("失败");
        }
    }


    @Test
    public void getComment() throws  SQLException{
        List<CommentVo> commentVoList = commentDao.getComment((long) 19);
        System.out.println(commentVoList);
    }

//    @Test
//    public void delete() throws SQLException{
//        int result = commentDao.delete((long) 4);
//    }


    @Test
    public void updatecomment() throws SQLException{
        int result = commentDao.updatecommnet(67l);
        System.out.println(result);
    }
}