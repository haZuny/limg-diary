package com.hayden.limg_diary.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TodayAndHistoryController {
    @GetMapping("/today")
    public String getToday(){
        return "today";
    }
}
