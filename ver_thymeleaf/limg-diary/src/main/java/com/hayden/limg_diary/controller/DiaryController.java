package com.hayden.limg_diary.controller;

import com.hayden.limg_diary.dto.DiaryDto;
import com.hayden.limg_diary.entity.User;
import com.hayden.limg_diary.service.DiaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;

@Controller
public class DiaryController {

    DiaryService diaryService;

    @Autowired
    public DiaryController(DiaryService diaryService) {
        this.diaryService = diaryService;
    }

    @GetMapping("/diary/write")
    public String getDiaryWrite(){
        return "diary_write";
    }

    @PostMapping("/diary/write")
    public String postDiaryWrite(DiaryDto.CreateDiaryDto diaryDto, @AuthenticationPrincipal User user){
        diaryService.createDiaryWithUserid(diaryDto,user.getUser_id());
        return "redirect:/today";
    }

}
