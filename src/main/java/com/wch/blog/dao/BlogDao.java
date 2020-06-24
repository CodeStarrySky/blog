package com.wch.blog.dao;

import com.wch.blog.bean.Blog;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface BlogDao {

    public List<Blog> selectAll();

    public Blog selectById(@Param("id") Long id);

    public Blog selectByTitle(@Param("title") String title);

    public int updateBlog(Blog blog);

    public int saveBlog(@Param("blog") Blog blog);

    //逻辑删除
    public int deleteById(@Param("id") Long id);

    public List<Blog> selectByBlog(Blog blog);

}
