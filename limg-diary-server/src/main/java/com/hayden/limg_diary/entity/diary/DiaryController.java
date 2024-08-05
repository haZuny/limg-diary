package com.hayden.limg_diary.entity.diary;

import com.hayden.limg_diary.entity.DefaultResponseDto;
import com.hayden.limg_diary.entity.diary.dto.*;
import com.hayden.limg_diary.entity.user.CustomUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.net.MalformedURLException;
import java.text.ParseException;

@RestController
@RequestMapping("/diary")
public class DiaryController {

    DiaryService diaryService;

    @Autowired
    public DiaryController(DiaryService diaryService) {
        this.diaryService = diaryService;
    }

    @PostMapping("/add")
    public ResponseEntity<DefaultResponseDto> postAdd(@RequestBody DiaryAddRequestDto diaryAddRequestDto, @AuthenticationPrincipal CustomUserDetails user) {

        return diaryService.diaryAdd(diaryAddRequestDto, user);
    }

    @GetMapping("/today")
    public ResponseEntity<DiaryTodayResponseDto> getToday(@AuthenticationPrincipal CustomUserDetails user) {
        return diaryService.getDiaryByToday(user);
    }

    @GetMapping("/{diaryId}")
    public ResponseEntity<DiaryIdResponseDto> getDiaryId(@PathVariable int diaryId, @AuthenticationPrincipal CustomUserDetails user) {
        return diaryService.getByDiaryId(diaryId, user);
    }

    @GetMapping("/month")
    public ResponseEntity<DiaryMonthResponseDto> getMonth(@RequestParam int year, @RequestParam int month, @AuthenticationPrincipal CustomUserDetails user) {
        return diaryService.getDiaryByMonth(year, month, user);
    }
//
    @GetMapping("/search")
    public ResponseEntity<DiarySearchResponseDto> getSearch(
            @RequestParam(value = "sdate", required = false) String sdate
            ,@RequestParam(value = "edate", required = false) String edate
            ,@RequestParam(value = "keyword", required = false) String keyword
            ,@RequestParam(value = "align", required = false) String align
            ,@AuthenticationPrincipal CustomUserDetails user) throws ParseException {
        return diaryService.diarySearch(sdate, edate, keyword, align, user);
    }

    @PatchMapping("/modify/{diaryId}")
    public ResponseEntity<DefaultResponseDto> diaryModify(@PathVariable int diaryId, @RequestBody DiaryModifyRequestDto diaryModifyRequestDto, @AuthenticationPrincipal CustomUserDetails user) {
        return diaryService.diaryModify(diaryId, diaryModifyRequestDto, user);
    }

    // image resource
    @GetMapping(value = "/img/{diaryId}", produces = MediaType.IMAGE_PNG_VALUE)
    public Resource getDiaryImage(@PathVariable int diaryId, @AuthenticationPrincipal CustomUserDetails userDetails) throws MalformedURLException {
        return diaryService.getDiaryImage(diaryId, userDetails);
    }

}
