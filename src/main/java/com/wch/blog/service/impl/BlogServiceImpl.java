package com.wch.blog.service.impl;

import com.wch.blog.bean.Blog;
import com.wch.blog.dao.BlogDao;
import com.wch.blog.service.BlogService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class BlogServiceImpl implements BlogService {

    @Resource
    BlogDao blogDao;
    public List<Blog> getAll(){

        List<Blog> blogs = blogDao.selectAll();

        return blogs;
    }

    public List<Blog> getByBlog(Blog blog){
        return blogDao.selectByBlog(blog);
    }

}
