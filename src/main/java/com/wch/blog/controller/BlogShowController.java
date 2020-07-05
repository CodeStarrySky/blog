package com.wch.blog.controller;

import com.wch.blog.service.BlogService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.annotation.Resource;

@Controller
public class BlogShowController {


    @Resource
    private BlogService blogService;

//    @GetMapping("/")
//    public String index(){
//        return "index";
//    }





}
