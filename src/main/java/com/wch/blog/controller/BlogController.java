package com.wch.blog.controller;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wch.blog.bean.*;
import com.wch.blog.exception.ValidationException;
import com.wch.blog.service.BlogService;
import com.wch.blog.service.TagService;
import com.wch.blog.service.TypeService;
import com.wch.blog.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.text.ParseException;
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
    @Resource
    UserService userService;

    @Resource
    TagService tagService;

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
        for (Blog blog : blogs) {
            System.out.println(blog.getUpdateTime());
        }
        PageInfo page = new PageInfo(blogs,NAVIGATE_PAGES);
        model.addAttribute("page",page);
        List<Type> types = typeService.getAll();
        model.addAttribute("types",types);

        return "admin/blogs";
    }

    @PostMapping("/search")
    public String search(Blog blog,@RequestParam(value="pn",required = false,defaultValue = "1") Integer pn,Model model){

        List<Type> types = typeService.getAll();
        model.addAttribute("types",types);
        PageHelper.startPage(pn, PAGE_SIZE);
        List<Blog> blogs = blogService.getByBlog(blog);
        PageInfo page = new PageInfo(blogs,NAVIGATE_PAGES);
        model.addAttribute("page",page);
        return "admin/blogs";
    }

    @GetMapping("/blogs-input")
    public String blogsInput(Model model) {
        List<Type> types = typeService.getAll();
        List<Tag> tags = tagService.getAll();

        model.addAttribute("types",types);
        model.addAttribute("tags",tags);
        return "admin/blogs-input";
    }

    @PostMapping("/blog")
    public String addBlog(Blog blog, HttpSession session, RedirectAttributes attributes) throws ParseException {
        User user = userService.finUser((String)session.getAttribute("loginUser"));
        if(user==null){
            attributes.addFlashAttribute("message","请先登录...");
            return "redirect:/admin";
        }
        blog.setUser(user);
        int i = blogService.save(blog);
        if(i>0){
            return "redirect:/admin/blogs";
        }else{
           throw new ValidationException("保存失败！");
        }

    }

    @GetMapping("/blog/{id}")
    public String blogEdit(@PathVariable Long id, Model model){
        Blog blog = blogService.findById(id);
        System.out.println(blog);
        if(blog==null){
            return "redirect:/admin/blog";
        }

        List<Type> types = typeService.getAll();
        List<Tag> tags = tagService.getAll();

        model.addAttribute("blog",blog);
        model.addAttribute("types",types);
        model.addAttribute("tags",tags);
        return "admin/blogs-input";
    }





}
