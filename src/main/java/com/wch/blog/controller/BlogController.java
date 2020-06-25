package com.wch.blog.controller;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wch.blog.bean.Blog;
import com.wch.blog.bean.Type;
import com.wch.blog.service.BlogService;
import com.wch.blog.service.TypeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RequestMapping("admin")
@Controller
public class BlogController {
    private final  Integer PAGE_SIZE = 10;
    private final  Integer NAVIGATE_PAGES = 1;
    @Resource
    BlogService blogService;
    @Resource
    TypeService typeService;

    @DeleteMapping("/blog/{id}")
    public String deleteBlog(@PathVariable Long id,@RequestParam("pn") Integer pn){
        int i = blogService.deleteBlog(id);
        if(i>0){
            return "redirect:/admin/blogs?pn="+pn;
        }else{
            throw new RuntimeException("删除失败！");
        }
    }
    @GetMapping("/blogs")
    public String blogs(@RequestParam(value="pn",required = false,defaultValue = "1") Integer pn, Model model){

        PageHelper.startPage(pn, PAGE_SIZE);


        List<Blog> blogs = blogService.getAll();
        PageInfo page = new PageInfo(blogs,NAVIGATE_PAGES);
        model.addAttribute("page",page);
        List<Type> types = typeService.getAll();
        model.addAttribute("types",types);
        System.out.println("111111111111111"+page);
        return "admin/blogs";
    }

    @PostMapping("/search")
    public String search(Blog blog,Model model){
        List<Type> types = typeService.getAll();
        model.addAttribute("types",types);
        List<Blog> blogs = blogService.getByBlog(blog);
        model.addAttribute("blogs",blogs);
        return "admin/blogs";
    }





}
