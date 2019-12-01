package com.scs.web.blog.service;

import com.scs.web.blog.factory.ServiceFactory;
import com.scs.web.blog.util.Result;
import org.junit.Test;

import java.sql.SQLException;

import static org.junit.Assert.*;

public class ArticleServiceTest {
    private ArticleService articleService = ServiceFactory.getArticleServiceInstance();
    @Test
    public void selectByKeywords() throws SQLException {
        Result rs = articleService.selectByKeywords("二");
        System.out.println(rs.getData());
    }
}