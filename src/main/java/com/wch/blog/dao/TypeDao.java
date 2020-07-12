package com.wch.blog.dao;

import com.wch.blog.bean.Type;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface TypeDao {

    public List<Type> selectList();

    public Type selectById(Long id);

    public int updateType(Type type);

    public int insertType(Type type);

    public int deleteType(Long id);

    public Type selectByName(String typeName);

    public List<Type> selectByLimit(@Param("number") Integer number);

    public List<Type> selectShowTypeAndBlog(@Param("typeId") Long typeId);
}
