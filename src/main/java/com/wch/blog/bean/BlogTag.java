package com.wch.blog.bean;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@NoArgsConstructor
@Data
public class BlogTag {

    private Long id;
    @NotNull
    private Long blogId;
    @NotNull
    private Long tagId;



}
