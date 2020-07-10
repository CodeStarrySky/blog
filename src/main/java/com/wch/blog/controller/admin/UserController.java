package com.wch.blog.controller.admin;

import com.sun.net.httpserver.HttpsConfigurator;
import com.wch.blog.bean.Msg;
import com.wch.blog.bean.User;
import com.wch.blog.exception.NotFoundException;
import com.wch.blog.exception.ValidationException;
import com.wch.blog.service.UserService;
import com.wch.blog.utils.MD5AndSHAUtils;
import org.apache.ibatis.annotations.Param;
import org.aspectj.weaver.ast.Not;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ClassUtils;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import java.io.File;
import java.util.List;

@Controller
@RequestMapping("admin")
public class UserController {

    @Resource
    UserService userService;

    @Value("${user-resources-path}")
    String resourcePath;

    @GetMapping("/pim")
    public String goPimPage(Model model, HttpSession session){
        User user = userService.finUser((String)session.getAttribute("loginUser"));
        System.out.println("pim++++++++++++"+user);
        model.addAttribute("user",user);
        return "admin/pim";
    }


    @ResponseBody
    @PutMapping("/pim")
    public Msg saveUserField(@RequestParam("fieldName") String fieldName,@RequestParam("fieldValue") String fieldValue,HttpSession session){

        User user = userService.finUser((String)session.getAttribute("loginUser"));
        if (fieldValue==null||fieldValue.equals("")){

            //提示失败并回显数据
            return Msg.fail().add("vi",fieldName+"不能为空").add("fieldValue",userService.selectByFieldName(fieldName,user.getId()));
        }
        int i = userService.updateUserField(fieldName,fieldValue,user.getId());
        if(i<1){
            return Msg.fail().add("vi",fieldName+"修改失败！");
        }
        return Msg.success();
    }


    //确认密码
    @GetMapping("/confirmPwd")
    @ResponseBody
    public Msg confirmPwd(@RequestParam("pwd") String pwd, HttpSession session){
        if(pwd.equals("")||pwd==null){
            return Msg.fail().add("vi","密码不能为空");
        }
        User confirmUser = userService.checkUser((String) session.getAttribute("loginUser"),pwd);
        if(confirmUser==null){
            return Msg.fail().add("vi","密码输入有误");
        }
        return Msg.success();
    }

    //修改密码
    @PutMapping("/changePwd")
    public String changePwd(HttpSession session, @RequestParam("oldPwd") String oldPwd, @RequestParam("newPwd") String newPwd, @RequestParam("confirmPwd") String confirmPwd, RedirectAttributes redirectAttributes){
        if(newPwd==null||newPwd.trim()==""||confirmPwd==null||confirmPwd.trim()==""||oldPwd==null||oldPwd.trim()==""){
            throw new ValidationException("密码不能为空或空字符串！");
        }
        User confirmUser = userService.checkUser((String) session.getAttribute("loginUser"),oldPwd);
        if(confirmUser==null){
            throw new ValidationException("密码有误！");
        }
        if(!newPwd.equals(confirmPwd)){
            throw new ValidationException("新密码与旧密码不相等！");
        }
        int i = userService.changePwd(MD5AndSHAUtils.md5AndSHA(newPwd), confirmUser.getId());
        if(i!=1){
            throw new ValidationException("系统故障，密码修改失败！");
        }
        session.removeAttribute("loginUser");
        redirectAttributes.addFlashAttribute("message","密码修改成功！请重新登录...");
        return "redirect: /admin";
    }

    @ResponseBody
    @PostMapping("/changeImage")
    public Msg changeHeadPro(@RequestParam("img") MultipartFile file,@RequestParam("fieldName") String fieldName, HttpSession session){
        if(file.isEmpty()){
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
        User user = userService.finUser((String)session.getAttribute("loginUser"));
        try{
            File fileDir = new File(resourcePath);
            String path = fileDir.getAbsolutePath();
            System.out.println(path);
            if(!fileDir.exists()){
                fileDir.mkdirs();
            }
            try{
                file.transferTo(new File(path,fileName));
                System.out.println(path);
                int i= userService.updateUserField(fieldName,path+File.separator+fileName,user.getId());
                if(i!=1){
                    return Msg.fail().add("vi","数据库更新出错！");
                }
                return Msg.success().add("path","/blog/images/user/"+fileName);
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
