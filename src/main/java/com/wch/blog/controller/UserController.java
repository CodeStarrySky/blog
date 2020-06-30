package com.wch.blog.controller;

import com.wch.blog.bean.User;
import com.wch.blog.exception.NotFoundException;
import com.wch.blog.service.UserService;
import org.aspectj.weaver.ast.Not;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import java.util.List;

@Controller
@RequestMapping("admin")
public class UserController {

    @Resource
    UserService userService;

    @GetMapping("/pim")
    public String goPimPage(Model model, HttpSession session){
        User user = userService.finUser((String)session.getAttribute("loginUser"));
        model.addAttribute("user",user);
        return "admin/pim";
    }


}
