package com.hayden.limg_diary.controller;


import com.hayden.limg_diary.dto.UserDto;
import com.hayden.limg_diary.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class HomeAndUserController {
    UserService userService;

    @Autowired
    public HomeAndUserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/")
    public String getHome(){
        return "home";
    }

    @GetMapping("/signup")
    public String getSignup(){
        return "signup";
    }

    @PostMapping("/signup")
    public String postSignup(UserDto.UserSignupDto userDto){
        userService.signUp(userDto);
        return "redirect:/";
    }
}
