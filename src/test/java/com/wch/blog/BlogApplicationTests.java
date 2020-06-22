package com.wch.blog;


import com.wch.blog.bean.User;
import com.wch.blog.service.UserService;
import com.wch.blog.utils.MD5AndSHAUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.security.NoSuchAlgorithmException;

@SpringBootTest
class BlogApplicationTests {


    @Test
    void contextLoads() {

        
    }
    @Autowired
    UserService userService;
    @Test
    public void checkUserTest(){
        User user = userService.checkUser("wch","123456");
        System.out.println(user);
    }

    @Test
    public void MD5AndSHATest() throws NoSuchAlgorithmException {

        String  str = MD5AndSHAUtils.md5AndSHA("123456");
        System.out.println(str);

    }


}
