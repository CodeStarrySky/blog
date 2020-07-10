package com.wch.blog.service;

import com.wch.blog.bean.Tag;

import java.util.List;

public interface TagService {
    public List<Tag> getAll();

    public Tag getTag(Long id);

    public int saveTag(Tag tag);

    public int updateTag(Tag tag);

    public int deleteTag(Long id);

    public Tag checkTagName(String tagName);

    public List<Tag> getLimitTag(Integer number);

    public List<Tag> getShowTagAndBlog();

}
