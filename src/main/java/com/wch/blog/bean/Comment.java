package com.wch.blog.bean;


import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

/**
 * 评论
 */
@Data
@NoArgsConstructor
//@TableName("comment")
public class Comment {

    private Long id;
    //昵称
    private String nickname;
    //邮箱
    private String email;
    //评论内容
    private String content;
    //头像
    private String headPortrait;
    //发表时间
    private Date createTime;
    //所属博客
    private Blog blog;
    //评论的用户
    private User user;
    //子评论
    private List<Comment> replyComments;
    //父评论
    private Comment parentComment;
}
