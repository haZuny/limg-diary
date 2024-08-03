package com.hayden.limg_diary.entity.diary;

import com.hayden.limg_diary.entity.DefaultResponseDto;
import com.hayden.limg_diary.entity.diary.dto.*;
import com.hayden.limg_diary.entity.user.CustomUserDetails;
import com.hayden.limg_diary.entity.user.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.Date;

@RestController
@RequestMapping("/diary")
public class DiaryController {

    DiaryService diaryService;
    @Autowired
    public DiaryController(DiaryService diaryService) {
        this.diaryService = diaryService;
    }

    @PostMapping("/add")
    public ResponseEntity<DefaultResponseDto> diaryAdd(@RequestBody DiaryAddRequestDto diaryAddRequestDto, @AuthenticationPrincipal CustomUserDetails user){

        return diaryService.diaryAdd(diaryAddRequestDto, user);
    }

    @GetMapping("/today")
    public ResponseEntity<DiaryTodayResponseDto> diaryToday(@AuthenticationPrincipal CustomUserDetails user){
        return diaryService.diaryToday(user);
    }

    @GetMapping("/{diaryId}")
    public ResponseEntity<DiaryIdResponseDto> diaryId(@PathVariable int diaryId, @AuthenticationPrincipal CustomUserDetails user){
        return diaryService.diaryId(diaryId, user);
    }

    @GetMapping("/month")
    public ResponseEntity<DiaryMonthResponseDto> diaryMonth(@RequestParam int year, @RequestParam int month, @AuthenticationPrincipal CustomUserDetails user){
        return diaryService.diaryMonth(year, month, user);
    }

    @GetMapping("/request")
    public ResponseEntity<DiaryRequestResponseDto> diaryRequset(@RequestParam(value = "sdate", required = false) String sdate, @RequestParam(value = "edate", required = false) String edate,@RequestParam(value = "keyword", required = false) String keyword, @RequestParam(value = "align", required = false) String align, @AuthenticationPrincipal CustomUserDetails user) throws ParseException {
        return diaryService.diaryRequest(sdate, edate, keyword, align, user);
    }

    @PatchMapping("/modify/{diaryId}")
    public ResponseEntity<DefaultResponseDto> diaryModify(@PathVariable int diaryId, @RequestBody DiaryModifyRequestDto diaryModifyRequestDto, @AuthenticationPrincipal CustomUserDetails user){
        return diaryService.diaryModify(diaryId, diaryModifyRequestDto, user);
    }
}