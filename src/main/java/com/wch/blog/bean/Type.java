package com.wch.blog.bean;


import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 分类
 */
@Data
@NoArgsConstructor
//@TableName("type")
public class Type {

    private Long id;
    private String typeName;

    private List<Blog> blogs;
}
