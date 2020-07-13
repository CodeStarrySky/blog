package com.wch.blog.service.impl;

import com.wch.blog.bean.Comment;
import com.wch.blog.dao.CommentDao;
import com.wch.blog.service.CommentService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {

    @Resource
    CommentDao commentDao;
    @Override
    public List<Comment> getShowByBlogId(Long blogId) {
        return commentDao.selectShowByBlogId(blogId);
    }

    @Transactional
    @Override
    public int saveComment(Comment comment) {
        return commentDao.saveComment(comment);
    }
}
