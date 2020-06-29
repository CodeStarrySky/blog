package com.wch.blog.bean;


import lombok.Data;
import lombok.NoArgsConstructor;

import java.awt.print.Pageable;
import java.sql.Time;
import java.util.Date;
import java.util.List;

/**
 * 博客
 */
@Data
@NoArgsConstructor
//@TableName("blog")
public class Blog {

//    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    //标题
    private String title;
    //内容
    private String  content;
    //首图地址
    private String firstFigure;
    //标记是否原创
    private boolean ifOriginal=true;
    //浏览次数
    private Integer views=0;
    //赞赏开关
    private boolean appreciates=false;

    //版权开关
    private boolean shareStatement=false;
    //评论开关
    private boolean commentabled=false;
    //是否发布
    private boolean published=true;
    //推荐开关
    private boolean recommend=false;
    //创建时间
    private Date createTime;
    //更新时间
    private Date updateTime;
    //标记是否删除 0表示已删除，1表示未删除
    private Integer flag=1;

    private Type type;

    private User user;
    //评论
    private List<Comment> comments;


    //标签
    private List<Tag> tags;


    private String tagIds;










}
