package com.wch.blog.service;

import com.wch.blog.bean.User;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;
import java.util.List;


public interface UserService {
    public List<User> selectList();

    public User finUser(String username);
    User checkUser(String username, String password);
}
