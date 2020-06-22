package com.wch.blog.bean;


import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

/**
 * 用户信息
 */
@Data
@NoArgsConstructor
//@TableName("comment")
public class User {

    private Long id;
    //昵称
    private String nickname;
    //用户名
    private String username;
    //密码
    private String password;
    //地址
    private String address;
    //邮箱
    private String email;
    //QQ号
    private String qq;
    //微信号
    private String weChat;
    //头像
    private String headPortrait;
    //类型
    private Integer type;
    //创建时间
    private Date createTime;
    //更新时间
    private Date updateTime;

    //公众号二维码
    private String publicQrCode;

    //收钱二维码
    private String collectionQrCode;

    //自我介绍
    private String introduceMyself;

    //个人博客网站介绍
    private String blogIntro;

    //职业
    private String profession;

    //简介1
    private String intro1;

    //简介1
    private String intro2;

    //简介1
    private String intro3;

    //博客
    private List<Blog> blogs;

    //评论
    private List<Comment> comments;
}
