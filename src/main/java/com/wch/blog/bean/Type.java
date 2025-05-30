package com.wch.blog.bean;


import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 * 分类
 */
@Data
@NoArgsConstructor
//@TableName("type")
public class Type {

    private Long id;
    @NotBlank(message = "分类名称不能为空")
    private String typeName;

    //每个分类对应blog的数量
    private Integer count;

    private List<Blog> blogs;
}
