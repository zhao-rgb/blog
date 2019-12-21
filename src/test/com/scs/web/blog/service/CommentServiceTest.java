package com.scs.web.blog.service;

import com.scs.web.blog.factory.ServiceFactory;
import com.scs.web.blog.util.Result;
import org.junit.Test;

import java.sql.SQLException;

import static org.junit.Assert.*;

public class CommentServiceTest {
   private CommentService commentService = ServiceFactory.getCommentServiceInstance();
//    @Test
//    public void getComment() throws SQLException {
//        Result rs = commentService.getComment(1);
//        System.out.println(rs.getData());
//    }
}