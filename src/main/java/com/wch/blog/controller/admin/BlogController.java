package com.wch.blog.controller.admin;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wch.blog.bean.*;
import com.wch.blog.exception.ValidationException;
import com.wch.blog.service.BlogService;
import com.wch.blog.service.TagService;
import com.wch.blog.service.TypeService;
import com.wch.blog.service.UserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.File;
import java.text.ParseException;
import java.util.List;

@RequestMapping("admin")
@Controller
public class BlogController {
//    private final  Integer PAGE_SIZE = 10;
//    private final  Integer NAVIGATE_PAGES = 1;
    @Value("${page-size}")
    Integer PAGE_SIZE;
    @Value("${navigate-pages}")
    Integer NAVIGATE_PAGES;
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
        PageInfo page = new PageInfo(blogs,NAVIGATE_PAGES);
        model.addAttribute("page",page);
        System.out.println(page);
        List<Type> types = typeService.getAll();
        model.addAttribute("types",types);

        return "admin/blogs";
    }

    @PostMapping("/search")
    public String search(Blog blog,@RequestParam(value="pn",required = false,defaultValue = "1") Integer pn,Model model){

        //回显查询条件
        model.addAttribute("queryConditions",blog);
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
    public String addBlog(@Valid Blog blog, HttpSession session, RedirectAttributes attributes) throws ParseException {
        User user = userService.finUser((String)session.getAttribute("loginUser"));
        if(user==null){
            attributes.addFlashAttribute("message","请先登录...");
            return "redirect:/admin";
        }
        blog.setUser(user);
        String path = blog.getFirstFigure();
        if("".equals(path)||path==null){

        }else{
            blog.setFirstFigure(path.replace("/blog/images/blog/",resourcePath));
        }
        System.out.println(blog);
        int i = blogService.save(blog);
        if(i>0){
            return "redirect:/admin/blogs";
        }else{
           throw new ValidationException("保存失败！");
        }

    }

    @GetMapping("/blog/{id}")
    public String blogEditPage(@PathVariable Long id, Model model){
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

    @PutMapping("/blog")
    public String blogUpdate(@Valid Blog blog) throws ParseException {
        String path = blog.getFirstFigure();
        if("".equals(path)||path==null){

        }else{
            blog.setFirstFigure(path.replace("/blog/images/blog/",resourcePath));
        }
        System.out.println(blog);
        blogService.updateBlog(blog);
        return "redirect:/admin/blogs";
    }
    @Value("${blog-resources-path}")
    String resourcePath;

    //首图
    @ResponseBody
    @PostMapping("/addAndEditFirstFigure")
    public Msg changeHeadPro(@RequestParam("file") MultipartFile file){
        if(file.isEmpty()||file==null){
            return Msg.fail().add("vi","文件不能为空");
        }
        String fileType = file.getContentType();
        if(!("image/gif".equals(fileType)||"image/jpeg".equals(fileType)||"image/png".equals(fileType))){
            return Msg.fail().add("vi","文件类型不匹配");
        }
        String fileName = file.getOriginalFilename();
        if(fileName.indexOf("\\") != -1){
            fileName = fileName.substring(fileName.lastIndexOf("\\"));
        }
        try{
            File fileDir = new File(resourcePath);
            String path = fileDir.getAbsolutePath();
            System.out.println("11111111111111111111"+path);
            if(!fileDir.exists()){
                fileDir.mkdirs();
            }
            try{
                file.transferTo(new File(path,fileName));
                System.out.println("222222222222222222"+path);
                return Msg.success().add("path","/blog/images/blog/"+fileName);
            }catch (Exception e){
                e.printStackTrace();
                return Msg.fail().add("vi","修改失败！");
            }
        }catch (Exception e){
            e.printStackTrace();
            return Msg.fail().add("vi","文件路径未找到！");
        }
    }



}
