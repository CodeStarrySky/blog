package com.wch.blog.bean;


import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
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
    @NotNull(message="昵称不能为空")
    private String nickname;
    //邮箱
    @Email(message = "必须是合法的邮箱地址")
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


    public String getHeadPortrait(){
        if(headPortrait==null||"".equals(headPortrait)){
            return headPortrait;
        }
        return headPortrait.replace("G:/blog/images/comments/", "/blog/images/comments/");
    }
}
