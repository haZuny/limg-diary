package com.hayden.limg_diary.controller;

import com.hayden.limg_diary.dto.DiaryDto;
import com.hayden.limg_diary.entity.Diary;
import com.hayden.limg_diary.entity.User;
import com.hayden.limg_diary.service.DiaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.net.MalformedURLException;
import java.security.Principal;
import java.time.LocalDate;
import java.util.Optional;

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
    public String postDiaryWrite(DiaryDto.CreateDiaryDto diaryDto, Model model, @AuthenticationPrincipal User user){
        Optional<Diary> diary = diaryService.createDiaryWithUserid(diaryDto,user.getUserid());
        model.addAttribute("diaryid", diary.get().getDiary_id());
        return "diary_write_done";
    }

    @GetMapping("/diary/update")
    public String getDiaryUpdate(Model model, @AuthenticationPrincipal User user){
        Diary diary = diaryService.findByUserAanDate(user, LocalDate.now()).get();
        model.addAttribute(diary);
        return "diary_update";
    }

    @PostMapping("/diary/update")
    public String postDiaryUpdate(DiaryDto.UpdateDiaryDto diaryDto, Model model){
        Optional<Diary> diary = diaryService.updateDiaryWithImage(diaryDto);
        model.addAttribute("diaryid", diary.get().getDiary_id());
        return "diary_write_done";
    }

}
