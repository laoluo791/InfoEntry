package com.ie.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class UrlController {

    @RequestMapping("/admin")
    public String admin(){
        return "admin";
    }

    @RequestMapping("/index")
    public String login(){
        return "index";
    }
    @RequestMapping("/login")
    public String index(){
        return "login";
    }

}
