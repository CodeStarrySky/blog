package com.wch.blog.service.impl;

import com.wch.blog.bean.User;
import com.wch.blog.dao.UserDao;
import com.wch.blog.service.UserService;
import com.wch.blog.utils.MD5AndSHAUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.security.NoSuchAlgorithmException;
import java.util.List;

@Service("userService")
public class UserServiceImpl implements UserService {
    @Resource
    private UserDao userDao;
    @Override
    public List<User> selectList() {
        List<User> list = userDao.selectList();
        return list;

    }

    /**
     * 校验登录密码
     * @param username
     * @param password
     * @return
     */
    @Override
    public User checkUser(String username, String password){
        User user = userDao.selectByCondition(username, MD5AndSHAUtils.md5AndSHA(password));
        return user;
    }

    @Transactional
    @Override
    public User finUser(String username) {
        return userDao.selectByName(username);
    }

    @Transactional
    @Override
    public int updateUserField(String fieldName, String fieldValue, Long id) {
        return userDao.updateUserField(fieldName,fieldValue,id);
    }

    @Override
    public String selectByFieldName(String fieldName, Long id) {
        return userDao.selectByFieldName(fieldName,id);
    }
}
