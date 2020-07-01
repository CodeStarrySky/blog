package com.wch.blog;

import com.wch.blog.dao.UserDao;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
public class UserDaoTest {

    @Resource
    UserDao userDao;
    @Test
    public void userTest(){
        System.out.println(userDao.selectById(3L));

    }

    @Test
    public void updateUserFieldTest(){
        System.out.println(userDao.updateUserField("nickname","wchaohui",3L));
    }





}
