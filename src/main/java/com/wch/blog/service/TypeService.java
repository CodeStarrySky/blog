package com.wch.blog.service;

import com.wch.blog.bean.Type;

import java.util.List;

public interface TypeService {

    public List<Type> getAll();

    public Type getType(Long id);

    public int saveType(Type type);

    public int updateType(Type type);

    public int deleteType(Long id);

    public Type checkTypeName(String typeName);

    public  List<Type> getLimitType(Integer number);
}
