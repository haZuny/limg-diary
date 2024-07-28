package com.hayden.limg_diary.entity.today_rate;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TodayRateRepository extends JpaRepository<TodayRateEntity, Integer> {
    TodayRateEntity findByRateNum(int today_rate);
}
