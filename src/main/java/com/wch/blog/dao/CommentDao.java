package com.wch.blog.dao;

import com.wch.blog.bean.Blog;
import com.wch.blog.bean.Comment;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CommentDao {


    public List<Comment> selectShowByCommentId(@Param("id") Long parentId);

    public List<Comment> selectShowByBlogId(@Param("blogId") Long blogId);

    public int saveComment(@Param("comment") Comment comment);

    public Comment selectShowByCommentParentId(@Param("parentId") Long parentId);

    public Comment selectShowByEmail(@Param("email") String email);

    public List<Comment> selectShowByUserId(@Param("userId") Long userId);
}
