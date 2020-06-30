package com.coolding.blog.interceptor;


import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @ClassName LoginInterceptor
 * @Author 酷酷的丁
 * @Date 2020-03-25 12:29
 */
//登录拦截器
public class LoginInterceptor extends HandlerInterceptorAdapter {
    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception {
        Object user = request.getSession().getAttribute("user");
        if(user == null){
            request.setAttribute("limit_mag","没有权限，请先登录");
            request.getRequestDispatcher("/admin").forward(request,response);
            System.out.println("拦截");
            response.sendRedirect("/admin");
             return false;
             }
       return true;
}
}
