package com.wch.blog.dao;



import com.wch.blog.bean.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.PutMapping;


import java.util.List;

@Mapper
public interface UserDao{

    public List<User> selectList();

    public User selectById(@Param("id") Long id);

    public User selectByName(@Param("username") String username);


    public User selectByCondition(String username, String password);


    public int updateUserField(@Param("feildName") String FeildName,@Param("feildValue") String FeildValue,@Param("id") Long id);

    public String selectByFieldName(@Param("fieldName") String fieldName,@Param("id") Long id);

    public User selectByPassword(@Param("pwd") String pwd, @Param("id") Long id);

    public int updatePwd(@Param("newPwd") String pwd,@Param("id") Long id);


}
