package com.wch.blog.controller;

import com.wch.blog.bean.Blog;
import com.wch.blog.bean.Type;
import com.wch.blog.service.BlogService;
import com.wch.blog.service.TypeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.util.List;

@RequestMapping("admin")
@Controller
public class BlogController {
    @Resource
    BlogService blogService;
    @Resource
    TypeService typeService;
    @GetMapping("/blogs")
    public String blogs(Model model){
        List<Type> types = typeService.getAll();
        model.addAttribute("types",types);
        System.out.println(model.getAttribute("blogs"));
        if(model.getAttribute("blogs")==null){
            List<Blog> blogs = blogService.getAll();
            model.addAttribute("blogs",blogs);
            System.out.println("11111111111111111111111");
        }
        return "admin/blogs";
    }

    @PostMapping("/search")
    public String search(Blog blog,Model model){
        System.out.println(blog);
        List<Blog> blogs = blogService.getByBlog(blog);
        model.addAttribute("blogs",blogs);
        return "forward:/admin/blogs";
    }




}
