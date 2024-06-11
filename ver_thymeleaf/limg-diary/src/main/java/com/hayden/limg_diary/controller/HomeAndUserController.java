package com.hayden.limg_diary.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeAndUserController {

    @GetMapping("/")
    public String getHome(){
        return "home";
    }

    @GetMapping("/signup")
    public String getSignup(){
        return "signup";
    }
}
