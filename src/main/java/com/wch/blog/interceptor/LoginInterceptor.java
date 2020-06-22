package com.wch.blog.interceptor;

import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        Object user = request.getSession().getAttribute("loginUser");
        if(user == null){
            request.setAttribute("message","没有权限，请先登录");
            request.getRequestDispatcher("/admin").forward(request,response);
            return false;
        }else {
            return true;
        }


    }
}
