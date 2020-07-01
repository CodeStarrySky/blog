package com.wch.blog.controller.admin;

import com.wch.blog.bean.Msg;
import com.wch.blog.bean.User;
import com.wch.blog.exception.NotFoundException;
import com.wch.blog.service.UserService;
import org.apache.ibatis.annotations.Param;
import org.aspectj.weaver.ast.Not;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import java.util.List;

@Controller
@RequestMapping("admin")
public class UserController {

    @Resource
    UserService userService;

    @GetMapping("/pim")
    public String goPimPage(Model model, HttpSession session){
        User user = userService.finUser((String)session.getAttribute("loginUser"));
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


}
