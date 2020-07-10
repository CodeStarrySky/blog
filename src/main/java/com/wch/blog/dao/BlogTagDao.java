package com.wch.blog.dao;

import com.wch.blog.bean.Blog;
import com.wch.blog.bean.BlogTag;
import com.wch.blog.bean.Tag;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface BlogTagDao {

    public List<Blog> selectByTagId(@Param("tag_id") Long tagId);

    public List<Tag> selectByBlogId(@Param("blog_id") Long blogId);

    public int deleteById(@Param("id") Long id);

    public List<Blog> selectShowByTagId(@Param("tag_id") Long tagId);
}
