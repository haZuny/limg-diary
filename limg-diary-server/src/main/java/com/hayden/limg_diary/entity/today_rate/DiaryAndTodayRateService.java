package com.hayden.limg_diary.entity.today_rate;

import com.hayden.limg_diary.entity.diary.DiaryEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DiaryAndTodayRateService {
    DiaryAndTodayRateRepository diaryAndTodayRateRepository;
    TodayRateRepository todayRateRepository;

    @Autowired
    public DiaryAndTodayRateService(DiaryAndTodayRateRepository diaryAndTodayRateRepository, TodayRateRepository todayRateRepository) {
        this.diaryAndTodayRateRepository = diaryAndTodayRateRepository;
        this.todayRateRepository = todayRateRepository;
    }

    public boolean DiaryAndTodayRateAdd(DiaryEntity diaryEntity, int todayRate){
        DiaryAndTodayRateEntity diaryAndTodayRateEntity = new DiaryAndTodayRateEntity();
        TodayRateEntity todayRateEntity = todayRateRepository.findByRateNum(todayRate);
        diaryAndTodayRateEntity.setRate(todayRateEntity);
        diaryAndTodayRateEntity.setDiary(diaryEntity);
        diaryAndTodayRateRepository.save(diaryAndTodayRateEntity);

        return true;

    }
}
