package com.wch.blog.dao;

import com.wch.blog.bean.Blog;
import com.wch.blog.bean.BlogTag;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface BlogDao {

    public List<Blog> selectAll();

    public Blog selectById( Long id);

    public Blog selectByTitle(@Param("title") String title);

    public int updateBlog(Blog blog);

    public int saveBlog(@Param("blog") Blog blog);

    public int saveBatchBlogTag(@Param("blogTags") List<BlogTag> blogTags );

    public int updateBatchBlogTag(@Param("blogTags") List<BlogTag> blogTags);

    public List<BlogTag> selectBlogTagByBlogId(@Param("blogId") Long blogId);

    //逻辑删除
    public int deleteById(@Param("id") Long id);

    public List<Blog> selectByBlog(Blog blog);

    public int deleteBlogTagByBlogId(@Param("blogId") Long blogId);

    public List<Blog> selectByUserId(@Param("userId") Long userId);

    public List<Blog> selectByShowAll();

    public List<Blog> selectTimeSequence();

    public List<Blog> selectShowByQuery(@Param("query") String query);

    public Blog selectTimeMaxAndMin();

    public List<Blog> selectShowByTypeId(@Param("typeId") Long typeId);

    public Blog selectShowById(@Param("id") Long id);

    public int selectShowCount();

    public int updateShowView(@Param("id") Long id);
}
