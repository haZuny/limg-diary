package com.hayden.limg_diary.entity.diary;

import com.hayden.limg_diary.entity.diary.dto.DiaryAddRequestDto;
import com.hayden.limg_diary.entity.diary.dto.DiaryAddResponseDto;
import com.hayden.limg_diary.entity.diary.dto.DiaryTodayResponseDto;
import com.hayden.limg_diary.entity.user.CustomUserDetails;
import com.hayden.limg_diary.entity.user.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/diary")
public class DiaryController {

    DiaryService diaryService;
    @Autowired
    public DiaryController(DiaryService diaryService) {
        this.diaryService = diaryService;
    }

    @PostMapping("/add")
    public ResponseEntity<DiaryAddResponseDto> diaryAdd(@RequestBody DiaryAddRequestDto diaryAddRequestDto, @AuthenticationPrincipal CustomUserDetails user){
        boolean res = diaryService.diaryAdd(diaryAddRequestDto, user);

        DiaryAddResponseDto diaryAddResponseDto = new DiaryAddResponseDto();

        //성공
        diaryAddResponseDto.setSuccess();
        if(res)return new ResponseEntity<>(diaryAddResponseDto, HttpStatus.CREATED);

        //실패
        diaryAddResponseDto.setFail();
        return new ResponseEntity<>(diaryAddResponseDto, HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/today")
    public ResponseEntity<DiaryTodayResponseDto> diaryToday(@AuthenticationPrincipal CustomUserDetails user){
        return diaryService.diaryToday(user);
    }
}