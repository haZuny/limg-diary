package com.hayden.limg_diary.entity.today_rate;

import com.hayden.limg_diary.entity.today_rate.dto.GetTodayrateListResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/todayrate")
public class TodayRateController {

    TodayRateService todayRateService;

    @Autowired
    public TodayRateController(TodayRateService todayRateService) {
        this.todayRateService = todayRateService;
    }

    @GetMapping("/list")
    public ResponseEntity<GetTodayrateListResponseDto> getTodayrateList(){
        return todayRateService.getTodayrateList();
    }
}
