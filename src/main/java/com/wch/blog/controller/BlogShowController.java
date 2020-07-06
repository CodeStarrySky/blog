package com.wch.blog.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mysql.cj.exceptions.NumberOutOfRange;
import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
import com.wch.blog.bean.Blog;
import com.wch.blog.bean.Tag;
import com.wch.blog.bean.Type;
import com.wch.blog.bean.User;
import com.wch.blog.service.BlogService;
import com.wch.blog.service.TagService;
import com.wch.blog.service.TypeService;
import com.wch.blog.service.UserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Controller
public class BlogShowController {


    @Resource
    private BlogService blogService;

    @Resource
    private UserService userService;

    @Resource
    private TypeService typeService;

    @Resource
    private TagService tagService;

    @Value("${page-size}")
    private Integer PAGE_SIZE;
    @Value("${navigate-pages}")
    private Integer NAVIGATE_PAGES;

    @Value("${right-type-number}")
    private Integer TYPE_NUMBER;

    @Value("${right-tag-number}")
    private Integer TAG_NUMBER;
    @Value("${blog-username}")
    private String BLOG_USERNAME;
    @GetMapping("/")
    public String index(@RequestParam(value="pn",required = false,defaultValue = "1") Integer pn, Model model){
        System.out.println(BLOG_USERNAME);
        User user = userService.finUser(BLOG_USERNAME);
        user.setUsername("");
        model.addAttribute("user",user);
        ////////////////////////////////////////////////////
        PageHelper.startPage(pn, PAGE_SIZE);
        List<Blog> blogs = blogService.getShowAll();
        List<Blog> showBlogs = new ArrayList<>();
        for(Blog blog : blogs){
            blog.setUser(null);
            blog.setCreateTime(null);
            showBlogs.add(blog);
        }
        PageInfo page = new PageInfo(showBlogs,NAVIGATE_PAGES);
        model.addAttribute("page",page);
        //////////////////////////////////////////////////////
        List<Type> types = typeService.getLimitType(TYPE_NUMBER);
        model.addAttribute("types",types);

        /////////////////////////////////////////////////////
        List<Tag> tags = tagService.getLimitTag(TAG_NUMBER);
        model.addAttribute("tags",tags);
        return "index";
    }





}
