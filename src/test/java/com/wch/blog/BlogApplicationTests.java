package com.wch.blog;


import com.wch.blog.bean.Blog;
import com.wch.blog.bean.BlogTag;
import com.wch.blog.bean.Type;
import com.wch.blog.bean.User;
import com.wch.blog.dao.BlogDao;
import com.wch.blog.service.BlogService;
import com.wch.blog.service.UserService;
import com.wch.blog.utils.MD5AndSHAUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

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

    @Test
    public void updateBlog(){
        Blog blog = new Blog();
        blog.setId(96L);
        blog.setIfOriginal(false);
        blog.setTitle("更新1");
        blog.setContent("更新内容11111111111111111");
        Type type = new Type();
        type.setId(9L);
        blog.setType(type);
        blog.setFirstFigure("更新1111");
        blog.setAppreciates(false);
        blog.setCommentabled(false);
        blog.setPublished(false);
        blog.setRecommend(false);
        System.out.println(blogDao.updateBlog(blog));
    }

    @Test
    public void updateBloTag(){
        List<BlogTag> list = new ArrayList<>();
        BlogTag blogTag = new BlogTag();
        blogTag.setId(59L);
        blogTag.setBlogId(96L);
        blogTag.setTagId(1L);
        BlogTag blogTag1 = new BlogTag();
        blogTag1.setId(58L);
        blogTag1.setBlogId(96L);
        blogTag1.setTagId(2L);
        list.add(blogTag);
        list.add(blogTag1);
        System.out.println(blogDao.updateBatchBlogTag(list));
    }

    @Test
    public void deleteBlogTagByBlogId(){
        System.out.println(blogDao.deleteBlogTagByBlogId(95L));
    }

    @Resource
    BlogService blogService;
    @Test
    public void getTimeSequence(){
        Map<Integer, List<Blog>> map = blogService.getTimeSequence();
        Set<Integer> integers = map.keySet();
        System.out.println("Set:::::::::::::"+integers);
        int s = 0;
        System.out.println("map::::::::::::::::"+map);
        for(Integer i : integers){
            List<Blog> list = map.get(i);

            System.out.println(i+"::::::::::::::::::"+list.size());
            int n=0;
            for (Blog blog : list){
                System.out.println(i+"******************"+blog.getUpdateTime());
                n++;
                s++;
            }
        }

    }

}
