package com.wch.blog.service;

import com.wch.blog.bean.Blog;
import org.apache.ibatis.annotations.Param;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

public interface BlogService {
    public List<Blog> getAll();

    public List<Blog> getByBlog(Blog blog);

    public int deleteBlog(@Param("id") Long id);

    public int  save(Blog blog) throws ParseException;

    public Blog findById(Long id);

    public int updateBlog(Blog blog) throws ParseException;

    public List<Blog> getShowAll();

    public Map<Integer,List<Blog>> getTimeSequence();

    //前端显示博客详情
    public Blog getShowBlog(Long id);

    public int getShowBlogCount();

    public List<Blog> showSearch(String query);

}
