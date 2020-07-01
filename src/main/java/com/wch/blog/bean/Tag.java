package com.wch.blog.bean;


import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 标签
 */
@Data
@NoArgsConstructor
//@TableName("tag")
public class Tag {

    private Long id;

    @NotBlank(message = "标签名称不能为空")
    private String tagName;

    //博客
    private List<Blog> blogs;


}

