package com.wch.blog.config;

import com.wch.blog.interceptor.LoginInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MyWebMvcConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginInterceptor())
                .addPathPatterns("/admin/**")
                .excludePathPatterns("/admin","/admin/login");
    }

    @Value("${blog-resources-path}")
    String blogPath;
    @Value("${user-resources-path}")
    String userPath;
    @Value("${comment-resources-path}")
    String commentPaht;
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/blog/images/blog/**").addResourceLocations("file:"+blogPath);
        registry.addResourceHandler("/blog/images/user/**").addResourceLocations("file:"+userPath);
        registry.addResourceHandler("/blog/images/comments/**").addResourceLocations("file:"+commentPaht);
    }
}
