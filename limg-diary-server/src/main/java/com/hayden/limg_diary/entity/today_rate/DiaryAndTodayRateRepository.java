package com.hayden.limg_diary.entity.today_rate;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DiaryAndTodayRateRepository extends JpaRepository<DiaryAndTodayRateEntity, Integer> {
}
