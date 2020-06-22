package com.wch.blog.dao;



import com.wch.blog.bean.User;
import org.apache.ibatis.annotations.Mapper;



import java.util.List;

@Mapper
public interface UserDao{

    public List<User> selectList();

    public User selectById(Long id);


    public User selectByCondition(String username, String password);
}
