package com.hayden.limg_diary.entity.today_rate;

import com.hayden.limg_diary.entity.diary.DiaryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DiaryAndTodayRateRepository extends JpaRepository<DiaryAndTodayRateEntity, Integer> {
    DiaryAndTodayRateEntity findByDiary(DiaryEntity diary);
}
