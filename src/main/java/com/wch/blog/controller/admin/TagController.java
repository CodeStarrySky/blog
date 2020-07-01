package com.wch.blog.controller.admin;

import com.wch.blog.bean.Msg;

import com.wch.blog.bean.Tag;



import com.wch.blog.service.TagService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;
@Controller
@RequestMapping("admin")
public class TagController {
    @Resource
    TagService tagService;

    @GetMapping("/tags")
    public String goTags(Model model){
        List<Tag> tags = tagService.getAll();
        model.addAttribute("tags",tags);
        return "admin/tags";
    }

    @GetMapping("/tag/{id}")
    @ResponseBody
    public Msg edit(@PathVariable("id") Long id){
        Tag tag = tagService.getTag(id);

        return Msg.success().add("tag",tag);
    }



    @GetMapping("/checkTagName")
    @ResponseBody
    public Msg checkTagName(@RequestParam String tagName) {
        String name = tagName.trim();
        if(name==null||name==""){
            return Msg.fail().add("vi","标签名称不能为空");
        }
        Tag tag = tagService.checkTagName(name);
        if(tag==null){
            return Msg.success();
        }else{
            return Msg.fail().add("vi","该标签已存在！");
        }
    }

    @PostMapping("/tag")
    @ResponseBody
    public Msg addTag(@Valid Tag tag) {
        System.out.println(tag);
        String name = tag.getTagName().trim();
        if(name==null||name==""){
            return Msg.fail().add("vi","标签名称不能为空");
        }
        Tag t = tagService.checkTagName(name);
        if(t==null){
            tag.setTagName(name);
            int i = tagService.saveTag(tag);
            if(i>0){
                return Msg.success().add("vi","标签添加成功！");
            }else {
                return Msg.fail().add("vi","标签添加失败！");
            }
        }else {
            return Msg.fail().add("vi","该标签已存在！");
        }
    }
    @PutMapping("/tag")
    @ResponseBody
    public Msg editTag(@Valid Tag tag) {
        System.out.println(tag);
        String name = tag.getTagName().trim();
        if(name==null||name==""){
            return Msg.fail().add("vi","标签名称不能为空");
        }
        Tag t = tagService.checkTagName(name);
        if(t==null){
            tag.setTagName(name);
            int i = tagService.updateTag(tag);
            if(i>0){
                return Msg.success().add("vi","标签修改成功！");
            }else {
                return Msg.fail().add("vi","标签修改失败！");
            }
        }else {
            return Msg.fail().add("vi","该标签已存在！");
        }
    }

    @DeleteMapping("/tag/{id}")
    @ResponseBody
    public Msg deleteTag(@PathVariable Long id){
        if(id<1){
            return Msg.fail().add("vi","不合法的id");
        }
        int i = tagService.deleteTag(id);
        if(i>0){
            return Msg.success().add("vi","删除成功！");
        }else{
            return Msg.fail().add("vi","删除失败！");
        }
    }
}
