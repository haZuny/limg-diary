package com.hayden.limg_diary.entity.diary;

import com.hayden.limg_diary.entity.diary.dto.DiaryAddRequestDto;
import com.hayden.limg_diary.entity.hashtag.DiaryAndHashtagService;
import com.hayden.limg_diary.entity.today_rate.DiaryAndTodayRateService;
import com.hayden.limg_diary.entity.today_rate.TodayRateEntity;
import com.hayden.limg_diary.entity.today_rate.TodayRateRepository;
import com.hayden.limg_diary.entity.user.CustomUserDetails;
import com.hayden.limg_diary.entity.user.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class DiaryService {
    DiaryRepository diaryRepository;
    DiaryAndTodayRateService diaryAndTodayRateService;
    DiaryAndHashtagService diaryAndHashtagService;
    @Autowired
    public DiaryService(DiaryAndHashtagService diaryAndHashtagService, DiaryRepository diaryRepository, DiaryAndTodayRateService diaryAndTodayRateService, TodayRateRepository todayRateRepository) {
        this.diaryRepository = diaryRepository;
        this.diaryAndTodayRateService = diaryAndTodayRateService;
        this.diaryAndHashtagService = diaryAndHashtagService;
    }

    public boolean diaryAdd(DiaryAddRequestDto diaryAddRequestDto, CustomUserDetails user){
        if(diaryAddRequestDto.getContent()==null) return false;
        if(diaryAddRequestDto.getDraw_style()==null) return false;

        //다이어리 엔티티에 컨텐츠 저장
        DiaryEntity diaryEntity = new DiaryEntity();
        diaryEntity.setContent(diaryAddRequestDto.getContent());
        diaryEntity.setUser(user.getUserEntity());
        diaryRepository.save(diaryEntity);

        //하루 평가 저장
        diaryAndTodayRateService.DiaryAndTodayRateAdd(diaryEntity, diaryAddRequestDto.getToday_rate());

        //해시태그 저장
        diaryAndHashtagService.DiaryAndHashtagAdd(diaryEntity, diaryAddRequestDto.getHashtag());

        return true;
    }
}
