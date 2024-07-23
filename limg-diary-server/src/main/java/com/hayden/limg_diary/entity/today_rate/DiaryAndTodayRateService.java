package com.hayden.limg_diary.entity.today_rate;

import com.hayden.limg_diary.entity.diary.DiaryEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DiaryAndTodayRateService {
    DiaryAndTodayRateRepository diaryAndTodayRateRepository;

    @Autowired
    public DiaryAndTodayRateService(DiaryAndTodayRateRepository diaryAndTodayRateRepository) {
        this.diaryAndTodayRateRepository = diaryAndTodayRateRepository;
    }

    public boolean DiaryAndTodayRateAdd(DiaryEntity diaryEntity, TodayRateEntity todayRateEntity){
        DiaryAndTodayRateEntity diaryAndTodayRateEntity = new DiaryAndTodayRateEntity();

        diaryAndTodayRateEntity.setDiary(diaryEntity);
        diaryAndTodayRateEntity.setRate(todayRateEntity);
        try{
            diaryAndTodayRateRepository.save(diaryAndTodayRateEntity);
        } catch (Exception e){
            return false;
        }
        return true;
    }
}
