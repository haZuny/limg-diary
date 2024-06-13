package com.hayden.limg_diary.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DiaryController {

    @GetMapping("/diary/write")
    public String getDiaryWrite(){
        return "diary_write";
    }
}
