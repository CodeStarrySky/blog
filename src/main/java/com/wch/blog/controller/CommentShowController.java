package com.wch.blog.controller;

import com.wch.blog.bean.Comment;
import com.wch.blog.bean.Msg;
import com.wch.blog.bean.User;
import com.wch.blog.service.CommentService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import javax.validation.ValidationException;
import java.io.File;
import java.util.List;

@Controller
public class CommentShowController {

    @Resource
    CommentService commentService;

    @Value("${comment-resources-path}")
    String commentPaht;



    @ResponseBody
    @PostMapping("/comment")
    public Msg saveComments(@Valid Comment comment){
        String path = comment.getHeadPortrait();
        if("".equals(path)||path==null){

        }else{
            comment.setHeadPortrait(path.replace("/blog/images/comment/",commentPaht));
        }
        System.out.println(comment);
        String content = comment.getContent();
        if(!content.startsWith("@")){
            comment.setParentComment(null);
        }
        System.out.println(comment);
        System.out.println(comment);
        int i = commentService.saveComment(comment);
        if(i>0){
            return Msg.success();
        }else{
            throw new ValidationException("系统原因，评论失败！");
        }
    }

    @GetMapping("/comments/{blogId}")
    public String comments(@PathVariable("blogId") Long blogId, Model model){

        List<Comment> comments = commentService.getShowByBlogId(blogId);
        model.addAttribute("comments",comments);
        System.out.println(comments);
        return "blog :: comments_div";
    }









    /**
     * 上传头像
     */
    @ResponseBody
    @PostMapping("/addAndEditHeadPortrait")
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
            File fileDir = new File(commentPaht);
            String path = fileDir.getAbsolutePath();
            System.out.println("11111111111111111111"+path);
            if(!fileDir.exists()){
                fileDir.mkdirs();
            }
            try{
                file.transferTo(new File(path,fileName));
                System.out.println("222222222222222222"+path);
                return Msg.success().add("path","/blog/images/comments/"+fileName);
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
