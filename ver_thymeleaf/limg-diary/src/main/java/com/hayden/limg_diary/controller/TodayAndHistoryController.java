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
import org.springframework.web.bind.annotation.PathVariable;

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
    public String getHistoryList(HistoryDto historyDto, Model model, @AuthenticationPrincipal User user){
        if (!historyDto.isEmpty()){
            System.out.println(historyDto.getSdate());
            model.addAttribute("sdate", historyDto.getSdateStr());
            model.addAttribute("edate", historyDto.getEdateStr());
            List<Diary> diaryList = diaryService.findByUserAndDateBetween(user, historyDto.getSdate(), historyDto.getEdate());
            model.addAttribute("diaries", diaryList);
        }
        return "history_list";
    }

    @GetMapping("/history/list/{diaryid}")
    public String getHistoryView(@PathVariable int diaryid, Model model, @AuthenticationPrincipal User user) throws IllegalAccessException {
        Optional<Diary> diaryOp = diaryService.getByDiaryid(diaryid);
        Diary diary = diaryOp.get();
        if (!diary.getUserid().equals(user.getUserid()))
            throw new IllegalAccessException("Not match diary with user");
        model.addAttribute("diary", diary);
        return "history_view";
    }
}
