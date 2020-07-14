package com.wch.blog.service.impl;

import com.wch.blog.bean.Comment;
import com.wch.blog.dao.CommentDao;
import com.wch.blog.service.CommentService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {

    @Resource
    CommentDao commentDao;
    @Override
    public List<Comment> getShowByBlogId(Long blogId) {
        List<Comment> comments = commentDao.selectShowByBlogId(blogId);

        return getNotPrentComment(comments);
    }

    /**
     * 遍历出顶级节点
     * @param comments
     * @return
     */
    public List<Comment> getNotPrentComment(List<Comment> comments){
        List<Comment> commentsNotPrent = new ArrayList<>();
        for(Comment comment : comments){
                Comment c = new Comment();
                BeanUtils.copyProperties(comment,c);
                commentsNotPrent.add(c);
        }
        //合并评论的各层子代到第一级子代集合中
        combineChildren(commentsNotPrent);


        return commentsNotPrent;


    }
    private List<Comment> tempReplys = new ArrayList<>();

    /**
     *
     * @param comments
     */
    private void combineChildren(List<Comment> comments){

        for (Comment comment : comments){
            List<Comment> replys = comment.getReplyComments();
            for(Comment reply :replys){
                //循环迭代，找出子代，存放在tempReplys中
                recursively(reply);
            }
            //修改顶级节点的reply集合为迭代后的集合
            comment.setReplyComments(tempReplys);
            //清除临时缓冲区
            tempReplys = new ArrayList<>();
        }

    }

    /**
     * 递归迭代出所有子节点
     * @param comment 被迭代的对象
     */
    private void recursively(Comment comment){
        tempReplys.add(comment);//顶节点添加到临时存放集合
        if(comment.getReplyComments().size()>0){
            List<Comment> replys = comment.getReplyComments();
            for(Comment reply : replys){
                tempReplys.add(reply);
                if(reply.getReplyComments().size()>0){
                    recursively(reply);
                }
            }
        }
    }



    @Transactional
    @Override
    public int saveComment(Comment comment) {
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String format = simpleDateFormat.format(date);
        try {
            comment.setCreateTime(simpleDateFormat.parse(format));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String content = comment.getContent();

        return commentDao.saveComment(comment);
    }
}
