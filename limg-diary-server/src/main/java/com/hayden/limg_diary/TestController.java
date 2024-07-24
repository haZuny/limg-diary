package com.hayden.limg_diary;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
    @GetMapping("/test")
    public String getTest(){
        return "success";
    }

    @GetMapping("/test/admin")
    public String getTestAdmin(){
        return "success";
    }
}
