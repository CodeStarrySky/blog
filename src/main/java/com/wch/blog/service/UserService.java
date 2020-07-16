package com.wch.blog.service;

import com.wch.blog.bean.Tag;
import com.wch.blog.bean.Type;
import com.wch.blog.bean.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;
import java.util.List;


public interface UserService {
    public List<User> selectList();

    public User finUser(String username);
    public User checkUser(String username, String password);

    public int updateUserField(String fieldName,String fieldValue,Long id);

    public String selectByFieldName(String fieldName, Long id);

    public User confirmByPassword(String pwd, Long id);

    public int changePwd(String newPwd,Long id);

    public String getHeadPortrait(String nickname, String email);



}
