package com.wch.blog;


import com.wch.blog.bean.Blog;
import com.wch.blog.bean.BlogTag;
import com.wch.blog.bean.User;
import com.wch.blog.dao.BlogDao;
import com.wch.blog.service.UserService;
import com.wch.blog.utils.MD5AndSHAUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

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

    @Resource
    BlogDao blogDao;
    @Test
    public void blogTagTest(){
        List<BlogTag> blogTags = new ArrayList<>();
        BlogTag blogTag1 = new BlogTag();
        blogTag1.setBlogId(1l);
        blogTag1.setTagId(2l);
        BlogTag blogTag2 = new BlogTag();
        blogTag2.setBlogId(1l);
        blogTag2.setTagId(8l);
        BlogTag blogTag3 = new BlogTag();
        blogTag3.setBlogId(1l);
        blogTag3.setTagId(9l);

        blogTags.add(blogTag1);
        blogTags.add(blogTag2);
        blogTags.add(blogTag3);
        blogDao.saveBatchBlogTag(blogTags);
    }

    @Test
    public void selectByIdTest(){
        System.out.println(blogDao.selectById(85L));
    }

    @Test
    public void selectByBlogTest(){
        List<Blog> blogs = blogDao.selectAll();
        blogs.forEach(System.out::println);
    }

}
