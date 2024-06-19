package com.hayden.limg_diary.controller;

import com.hayden.limg_diary.dto.HistoryDto;
import com.hayden.limg_diary.entity.Diary;
import com.hayden.limg_diary.entity.User;
import com.hayden.limg_diary.service.DiaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Controller
public class TodayAndHistoryController {

    DiaryService diaryService;

    @Autowired
    public TodayAndHistoryController(DiaryService diaryService) {
        this.diaryService = diaryService;
    }

    @GetMapping("/today")
    public String getToday(Model model, @AuthenticationPrincipal User user){
        Optional<Diary> diary = diaryService.findByUserAndDate(user, LocalDate.now());
        if (diary.isPresent()) model.addAttribute("diary", diary.get());


        return "today";
    }

    @GetMapping("/history/list")
    public String getHistoryList(HistoryDto historyDto){
        if (historyDto != null){
//            List<Diary> diaryList = diaryService.findByUserAndDateBetween();
        }
        return "history_list";
    }
}
