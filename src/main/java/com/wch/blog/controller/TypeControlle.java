package com.wch.blog.controller;

import com.wch.blog.bean.Msg;
import com.wch.blog.bean.Type;
import com.wch.blog.dao.TypeDao;
import com.wch.blog.service.TypeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("admin")
public class TypeControlle {

    @Resource
    TypeService typeService;

    @GetMapping("/types")
    public String goTypes(Model model){
        List<Type> types = typeService.getAll();
        model.addAttribute("types",types);
        return "admin/types";
    }

    @GetMapping("/type/{id}")
    @ResponseBody
    public Msg edit(@PathVariable("id") Long id){
        Type type = typeService.getType(id);

        return Msg.success().add("type",type);
    }



    @GetMapping("/checkTypeName")
    @ResponseBody
    public Msg checkTypeName(@RequestParam String typeName) {
        String name = typeName.trim();
        if(name==null||name==""){
            return Msg.fail().add("vi","分类名称不能为空");
        }
        Type type = typeService.checkTypeName(name);
        if(type==null){
            return Msg.success();
        }else{
            return Msg.fail().add("vi","该分类已存在！");
        }
    }

    @PostMapping("/type")
    @ResponseBody
    public Msg addType(Type type) {
        System.out.println(type);
        String name = type.getTypeName().trim();
        if(name==null||name==""){
            return Msg.fail().add("vi","分类名称不能为空");
        }
        Type t = typeService.checkTypeName(name);
        if(t==null){
            type.setTypeName(name);
            int i = typeService.saveType(type);
            if(i>0){
                return Msg.success().add("vi","分类添加成功！");
            }else {
                return Msg.fail().add("vi","分类添加失败！");
            }
        }else {
            return Msg.fail().add("vi","该分类已存在！");
        }
    }
    @PutMapping("/type")
    @ResponseBody
    public Msg editType(Type type) {
        System.out.println(type);
        String name = type.getTypeName().trim();
        if(name==null||name==""){
            return Msg.fail().add("vi","分类名称不能为空");
        }
        Type t = typeService.checkTypeName(name);
        if(t==null){
            type.setTypeName(name);
            int i = typeService.updateType(type);
            if(i>0){
                return Msg.success().add("vi","分类修改成功！");
            }else {
                return Msg.fail().add("vi","分类修改失败！");
            }
        }else {
            return Msg.fail().add("vi","该分类已存在！");
        }
    }

    @DeleteMapping("/type/{id}")
    @ResponseBody
    public Msg deleteType(@PathVariable Long id){
        if(id<1){
            return Msg.fail().add("vi","不合法的id");
        }
        int i = typeService.deleteType(id);
        if(i>0){
            return Msg.success().add("vi","删除成功！");
        }else{
            return Msg.fail().add("vi","删除失败！");
        }
    }


}
