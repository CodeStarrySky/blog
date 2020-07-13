package com.wch.blog.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mysql.cj.exceptions.NumberOutOfRange;
import com.sun.org.apache.xpath.internal.operations.Mod;
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
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    /**
     * 将常用的数据放到session中
     * @param session
     */
    @ModelAttribute
    public void getPublicData(HttpSession session){
        if(session.getAttribute("data")==null){
            System.out.println("************************************************");
            User user = userService.finUser(BLOG_USERNAME);
            user.setUsername("");
            List<Type> types = typeService.getLimitType(TYPE_NUMBER);
            List<Tag> tags = tagService.getLimitTag(TAG_NUMBER);
            Map<String,Object> data = new HashMap<>();
            int blogCount = blogService.getShowBlogCount();
            int tagCount = tagService.getShowTagCount();
            data.put("user",user);
            data.put("types",types);
            data.put("tags",tags);
            data.put("blogCount",blogCount);
            data.put("tagCount",tagCount);
            session.setAttribute("data",data);
        }


    }

    /**
     * 显示博客列表
     * @param pn
     * @param model
     * @return
     */
    @GetMapping("/")
    public String index(@RequestParam(value="pn",required = false,defaultValue = "1") Integer pn, Model model){
        PageHelper.startPage(pn, PAGE_SIZE);
        List<Blog> blogs = blogService.getShowAll();
        System.out.println(blogs.size()+"*****"+blogs);
        PageInfo page = new PageInfo(blogs,NAVIGATE_PAGES);
        model.addAttribute("page",page);
        return "index";
    }
    @GetMapping("/pn/{pn}")
    public String indexPn(@PathVariable(value="pn",required = false) Integer pn, Model model){
        if(pn==1){
            return "redirect:/";
        }
        PageHelper.startPage(pn, PAGE_SIZE);
        List<Blog> blogs = blogService.getShowAll();
        System.out.println(blogs.size()+"*****"+blogs);
        PageInfo page = new PageInfo(blogs,NAVIGATE_PAGES);
        model.addAttribute("page",page);
        return "index";
    }
    @GetMapping("/archive")
    public String archive(Model model){
        Map<Integer, List<Blog>> blogMap = blogService.getTimeSequence();
        model.addAttribute("blogMap",blogMap);
        return "archive";
    }

    @GetMapping("/tags")
    public String tags(Model model){
        List<Tag> tags = tagService.getShowTagAndBlog(null);
        model.addAttribute("tags",tags);
        System.out.println(tags);
        return "tags";
    }
    @GetMapping("/tag/{tagId}")
    public String tag(@PathVariable("tagId") Long tagId, Model model){
        List<Tag> tags = tagService.getShowTagAndBlog(tagId);
        model.addAttribute("tags",tags);
        System.out.println(tags);
        return "tags";
    }
    @GetMapping("/types")
    public String types(Model model){
        List<Type> types = typeService.getShowType(null);
        model.addAttribute("types",types);
        System.out.println(types);
        return "types";
    }
    @GetMapping("/type/{typeId}")
    public String type(@PathVariable("typeId") Long typeId, Model model){
        List<Type> types = typeService.getShowType(typeId);
        model.addAttribute("types",types);
        System.out.println(types);
        return "types";
    }
    @GetMapping("/about")
    public String about(Model model){

        return "about";
    }
    @GetMapping("/blog/{id}")
    public String blog(Model model,@PathVariable("id") Long id){
        Blog blog = blogService.getShowBlog(id);
        model.addAttribute("blog",blog);
        System.out.println(blog);
        System.out.println(id);
        return "blog";
    }

    String query = null;

    @PostMapping("/search")
    public String search(@RequestParam(value="pn",required = false,defaultValue = "1") Integer pn, @RequestParam("query") String query, Model model){
        if(query==null||"".equals(query)){
            return "redirect:/";
        }
        List<Blog> blogs = blogService.showSearch(query);
        model.addAttribute("blogs",blogs);
        return "search";
    }






}
