package com.hayden.limg_diary.entity.hashtag;

import com.hayden.limg_diary.entity.hashtag.dto.GetDiaryByTagResponseDto;
import com.hayden.limg_diary.entity.user.CustomUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
@RequestMapping("/hashtag")
public class HashtagController {

    DiaryAndHashtagService diaryAndHashtagService;

    @Autowired
    public HashtagController(DiaryAndHashtagService diaryAndHashtagService) {
        this.diaryAndHashtagService = diaryAndHashtagService;
    }

    @GetMapping("/search")
    public ResponseEntity<GetDiaryByTagResponseDto> getSearch(@RequestParam(required = false)ArrayList<String> tags, @AuthenticationPrincipal CustomUserDetails userDetails){
        return diaryAndHashtagService.getDiaryByTag(tags, userDetails);
    }
}
