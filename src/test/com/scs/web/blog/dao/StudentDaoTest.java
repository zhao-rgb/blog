package com.scs.web.blog.dao;

import com.scs.web.blog.factory.DaoFactory;
import com.scs.web.blog.util.JSoupSpider;
import org.junit.Test;

import java.sql.SQLException;
import java.time.LocalDateTime;

import static org.junit.Assert.*;

//public class StudentDaoTest {
//    private StudentDao studentDao  =DaoFactory.getStudentDaoInstance();
//
//    @Test
//    public void insert() throws SQLException {
//        Student student = new Student();
//        student.setId(1);
//        student.setAvatar("125");
//        student.setUsername("zxl");
//        student.setCreateTime(LocalDateTime.now());
//        System.out.println(DaoFactory.getStudentDaoInstance().insert(student));
//    }
//    @Test
//    public void batchInsert() throws SQLException{
//        int[] n = studentDao.batchInsert(JSoupSpider.getStudent());
//        assertEquals(24,n.length);
//    }
//
//}