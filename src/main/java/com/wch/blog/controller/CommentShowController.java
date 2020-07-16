package com.wch.blog.controller;

import com.wch.blog.bean.Comment;
import com.wch.blog.bean.Msg;
import com.wch.blog.bean.User;
import com.wch.blog.service.CommentService;
import com.wch.blog.service.UserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import javax.validation.ValidationException;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.io.File;
import java.util.List;

@Controller
public class CommentShowController {

    @Resource
    CommentService commentService;
    @Resource
    UserService userService;

    @Value("${comment-resources-path}")
    String commentPaht;

    @Value("${comment-default-avatar-path}")
    private String defaultAvatar;

    @Transactional
    @ResponseBody
    @PostMapping("/comment")
    public Msg saveComments(@Valid Comment comment,HttpSession session){
        Object loginUser = session.getAttribute("loginUser");
        if(loginUser!=null){
            String nickname = comment.getNickname();
            String email = comment.getEmail();
            String headPortrait = userService.getHeadPortrait(nickname, email);
            if(headPortrait==null||"".equals(headPortrait)){
                comment.setFlag(false);
            }else {
                comment.setHeadPortrait(headPortrait.replace("user","comments"));
                comment.setFlag(true);
            }
        }
        String headPortrait = comment.getHeadPortrait();
        if(headPortrait==null||"".equals(headPortrait)){
            String email = comment.getEmail();
            Comment confirmComment = commentService.getShowByEmail(email);
            if(confirmComment!=null){
                comment.setHeadPortrait(confirmComment.getHeadPortrait().replace("/blog/images/comments/",commentPaht));
            }else{
                comment.setHeadPortrait(defaultAvatar);
            }
        }else{
            comment.setHeadPortrait(headPortrait.replace("/blog/images/comments/",commentPaht));
        }
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
        return "blog :: comments_div";
    }

    @GetMapping("/comments/user/{userId}")
    public String commentUserIds(@PathVariable("userId") Long userId, Model model){
        List<Comment> comments = commentService.getShowByUserId(userId);
        model.addAttribute("comments",comments);
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
