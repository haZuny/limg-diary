package com.hayden.limg_diary.entity.diary;

import com.hayden.limg_diary.entity.diary.dto.DiaryAddRequestDto;
import com.hayden.limg_diary.entity.diary.dto.DiaryAddResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/diary")
public class DiaryController {

    DiaryService diaryService;
    @Autowired
    public DiaryController(DiaryService diaryService) {
        this.diaryService = diaryService;
    }

    @PostMapping("/add")
    public ResponseEntity<DiaryAddResponseDto> diaryAdd(@RequestBody DiaryAddRequestDto diaryAddRequestDto){
        boolean res = diaryService.diaryAdd(diaryAddRequestDto);

        DiaryAddResponseDto diaryAddResponseDto = new DiaryAddResponseDto();

        //성공
        diaryAddResponseDto.setSuccess();
        if(res)return new ResponseEntity<>(diaryAddResponseDto, HttpStatus.CREATED);

        //실패
        diaryAddResponseDto.setFail();
        return new ResponseEntity<>(diaryAddResponseDto, HttpStatus.BAD_REQUEST);
    }
}
