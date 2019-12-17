package com.scs.web.blog.dao;

import com.scs.web.blog.factory.DaoFactory;
import org.junit.Test;

import java.sql.SQLException;

import static org.junit.Assert.*;

public class LikeDaoTest {
    private LikeDao likeDao = DaoFactory.getLikeDaoInstance();

    @Test
    public void insertLike() {
    }

    @Test
    public void selectLikes() {
    }

    @Test
    public void addLikes() {
    }

    @Test
    public void deleteLike() throws SQLException {
        boolean flag =likeDao.deleteLike(14,1);
        System.out.println(flag);
    }

    @Test
    public void cancelLike() {
    }
}