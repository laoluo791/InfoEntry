package com.ie.interceptor;

import com.ie.pojo.Admin;
import com.ie.pojo.User;
import com.ie.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    public AdminService adminService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        //System.out.println("进入拦截器...");
        Cookie[] cookies = request.getCookies();
        Admin admin = new Admin();
        boolean flag = false;
        for(Cookie cookie : cookies){
            if(cookie.getName().equals("name")){
                flag = true;
                admin.setName(cookie.getValue());
            }
            else if(cookie.getName().equals("password")){
                admin.setPassword(cookie.getValue());
            }
        }
        boolean login = false;
        if(flag){
            login = adminService.login(admin);
        }
        if(login){
            request.getSession().setAttribute("USER",admin);
            return true;
        }
        Admin user = (Admin) request.getSession().getAttribute("USER");
        if(user == null){
            response.sendRedirect("login");
            return false;
        }
        return true;
    }
}
