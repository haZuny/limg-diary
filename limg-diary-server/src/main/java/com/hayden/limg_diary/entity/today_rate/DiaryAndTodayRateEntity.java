package com.hayden.limg_diary.entity.today_rate;

import com.hayden.limg_diary.entity.BaseTimeEntity;
import com.hayden.limg_diary.entity.diary.DiaryEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="diary_and_today_rate")
@Getter
@Setter
public class DiaryAndTodayRateEntity extends BaseTimeEntity {
    @ManyToOne
    @JoinColumn(name="DIARY_ID")
    private DiaryEntity diary;

    @ManyToOne
    @JoinColumn(name="RATE_ID")
    private TodayRateEntity rate;
}
