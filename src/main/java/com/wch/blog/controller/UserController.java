package com.wch.blog.controller;

import com.wch.blog.bean.User;
import com.wch.blog.exception.NotFoundException;
import com.wch.blog.service.UserService;
import org.aspectj.weaver.ast.Not;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

import java.util.List;

@Controller
public class UserController {

    @Autowired
    UserService userService;

    @ResponseBody
    @GetMapping("/user/list")
    public List<User> selectUsers(){
        List<User> users = userService.selectList();
        return users;
    }

    @GetMapping("/blogs")
    public String goBlogs(){


        return "admin/blogs";
    }
    @GetMapping("/blogs-input")
    public String goinput(){


        return "admin/blogs-input";
    }
    @GetMapping("/blog")
    public String goBlog(){


        return "blog";
    }

}
