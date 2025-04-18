package com.wch.blog.controller.admin;

import com.wch.blog.bean.User;
import com.wch.blog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("admin")
public class LoginController {
    @Resource
    private UserService userService;
    @GetMapping
    public String loginPage(HttpSession session) {
        Object loginUser = session.getAttribute("loginUser");
        if(loginUser==null){
            return "admin/login";
        }else{
            return "redirect:/admin/blogs";
        }

    }
    @PostMapping("/login")
    public String login(@RequestParam("username") String username,
                        @RequestParam("password") String password,
                        HttpSession session,
                        RedirectAttributes attributes){
        User user = userService.checkUser(username,password);
        if(user!=null){

            session.setAttribute("loginUser",user.getUsername());
            return "redirect:/admin/blogs";
        }else{
            attributes.addFlashAttribute("message","用户名或密码错误");
            return "redirect:/admin";
        }

    }

    @GetMapping("/logout")
    public String logout(HttpSession session){
        session.removeAttribute("loginUser");
        return "redirect:/admin";
    }



}
