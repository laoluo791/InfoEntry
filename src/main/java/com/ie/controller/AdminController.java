package com.ie.controller;

import com.ie.pojo.Admin;
import com.ie.publicmodel.Info;
import com.ie.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("admin")
public class AdminController {

    @Autowired
    private AdminService adminService;


    @ResponseBody
    @RequestMapping("/login")
    public Info login(Admin admin, boolean saveState, HttpServletRequest request, HttpServletResponse response){
        boolean login = adminService.login(admin);
        Info info = new Info(0, "登录失败");
        if(login){
            info.setState(1);
            info.setMsg("登录成功");
            //添加进session
            request.getSession().setAttribute("USER",admin);
            if(saveState){
                //添加进cookie
                Cookie name = new Cookie("name", admin.getName());
                name.setMaxAge(Integer.MAX_VALUE);
                name.setPath("/");//设置作用域
                response.addCookie(name);
                Cookie password = new Cookie("password", admin.getPassword());
                password.setMaxAge(Integer.MAX_VALUE);
                password.setPath("/");//设置作用域
                response.addCookie(password);
            }
        }
        return info;
    }

    @ResponseBody
    @RequestMapping("getSessionUserMsg")
    public Info getSessionUserMsg(HttpSession session){
        Admin admin = (Admin) session.getAttribute("USER");
        return new Info(1,admin.getName());
    }

    @ResponseBody
    @RequestMapping("exitUser")
    public Info exitUser(HttpSession session ,HttpServletRequest request, HttpServletResponse response){
        //移除session
        session.removeAttribute("USER");
        //删除重置cookie
        Cookie[] cookies = request.getCookies();
        for(Cookie cookie : cookies){
            if (cookie.getName().equals("password")){
                //重置密码
                cookie.setValue(null);
                cookie.setMaxAge(0);// 立即销毁cookie
                cookie.setPath("/");
                response.addCookie(cookie);
            }
            else if (cookie.getName().equals("name")){
                //重置名字
                cookie.setValue(null);
                cookie.setMaxAge(0);// 立即销毁cookie
                cookie.setPath("/");
                response.addCookie(cookie);
            }
        }
        return new Info(1,"登出成功");
    }


}
