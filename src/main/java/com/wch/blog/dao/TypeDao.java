package com.wch.blog.dao;

import com.wch.blog.bean.Type;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface TypeDao {

    public List<Type> selectList();

    public Type selectById(Long id);

    public int updateType(Type type);

    public int insertType(Type type);

    public int deleteType(Long id);

    public Type selectByName(String typeName);
}
