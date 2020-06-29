package com.wch.blog.dao;



import com.wch.blog.bean.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;


import java.util.List;

@Mapper
public interface UserDao{

    public List<User> selectList();

    public User selectById(@Param("id") Long id);

    public User selectByName(@Param("username") String username);


    public User selectByCondition(String username, String password);
}
