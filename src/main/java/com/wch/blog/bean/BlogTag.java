package com.wch.blog.bean;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class BlogTag {

    private Long id;
    private Long blogId;
    private Long tagId;



}
