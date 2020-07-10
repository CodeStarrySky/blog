package com.wch.blog.dao;

import com.wch.blog.bean.Tag;
import com.wch.blog.bean.Type;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface TagDao {

    public List<Tag> selectAll();

    public Tag selectById(@Param("id") Long id);

    public Tag selectByName(@Param("tagName") String tagName);

    public int saveTag(@Param("tag") Tag tag);

    public int updateTag(@Param("tag") Tag tag);

    public int deleteTag(@Param("id") Long id);

    public List<Tag> selectByLimit(@Param("number") Integer number);

    public List<Tag> selectShowTagAndBlog();

}
