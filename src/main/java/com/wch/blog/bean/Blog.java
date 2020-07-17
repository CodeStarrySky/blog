package com.wch.blog.bean;


import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
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
    @NotBlank(message = "标题不能为空")
    private String title;
    //内容
    @NotBlank(message = "内容不能为空")
    private String  content;

    //简介
    @NotBlank(message = "博客简介不能为空")
    private String introduction;
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
//    @NotNull(message = "生成创建事间有误")
    private Date createTime;
    //更新时间
//    @NotNull(message = "生成更新事件有误")
    private Date updateTime;
    //标记是否删除 0表示已删除，1表示未删除
    private Integer flag=1;
    @NotNull(message = "需要一个分类")
    private Type type;
//    @NotNull(message = "属于一个用户")
    private User user;
    //评论
    private List<Comment> comments;


    //标签
    private List<Tag> tags;


    private String tagIds;

    public String getFirstFigure(){
        if(firstFigure==null||"".equals(firstFigure)){
            return firstFigure;
        }
        if(firstFigure.startsWith("/opt")){
            return firstFigure.replace("/opt/blog/images/blog/", "/blog/images/blog/");
        }else{
            return firstFigure;
        }

    }

    //所有博客最新的时间
    private Date maxTime;
    //最早发布博客的时间
    private Date minTime;








}
