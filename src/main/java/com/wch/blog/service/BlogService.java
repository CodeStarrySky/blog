package com.wch.blog.service;

import com.wch.blog.bean.Blog;

import java.util.List;

public interface BlogService {
    public List<Blog> getAll();

    public List<Blog> getByBlog(Blog blog);
}
