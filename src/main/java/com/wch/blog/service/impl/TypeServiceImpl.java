package com.wch.blog.service.impl;

import com.wch.blog.bean.Type;
import com.wch.blog.dao.TypeDao;
import com.wch.blog.exception.NotFoundException;
import com.wch.blog.service.TypeService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
public class TypeServiceImpl implements TypeService {

    @Resource
    TypeDao typeDao;
    @Override
    public List<Type> getAll() {
        List<Type> types = typeDao.selectList();
        return types;
    }

    @Override
    public Type getType(Long id) {
        return typeDao.selectById(id);
    }

    @Override
    public int saveType(Type type) {
        return typeDao.insertType(type);
    }

    @Override
    public int updateType(Type type) {
        Type t = typeDao.selectById(type.getId());
        if(t == null){
            throw new NotFoundException("没有该类型，您不能进行更新！");
        }
        BeanUtils.copyProperties(type,t);
        return typeDao.updateType(t);
    }

    @Override
    public int deleteType(Long id) {
        return typeDao.deleteType(id);
    }

    @Override
    public Type checkTypeName(String typeName) {
        return typeDao.selectByName(typeName);
    }

    @Transactional
    @Override
    public List<Type> getLimitType(Integer number){
        return typeDao.selectByLimit(number);
    }


    @Override
    public List<Type> getShowType() {
        return typeDao.selectShowTypeAndBlog();
    }
}
