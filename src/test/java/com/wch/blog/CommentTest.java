package com.wch.blog;

import com.wch.blog.dao.CommentDao;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
public class CommentTest {

    @Resource
    CommentDao commentDao;

    @Test
    public void selectTest(){

        System.out.println(commentDao.selectShowByBlogId(109L));
    }



}
