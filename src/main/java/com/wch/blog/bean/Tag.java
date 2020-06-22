package com.wch.blog.bean;


import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 标签
 */
@Data
@NoArgsConstructor
//@TableName("tag")
public class Tag {

    private Long id;

    private String tagName;

    //博客
    private List<Blog> blogs;


}

