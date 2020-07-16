package com.wch.blog.service;

import com.wch.blog.bean.Comment;

import java.util.List;

public interface CommentService {

    public List<Comment> getShowByBlogId(Long blogId);

    public int saveComment(Comment comment);

    public Comment getShowByEmail(String email);

    public List<Comment> getShowByUserId(Long userId);

}
