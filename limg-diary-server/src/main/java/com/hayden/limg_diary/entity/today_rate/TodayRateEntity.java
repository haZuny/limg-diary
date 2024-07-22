package com.hayden.limg_diary.entity.today_rate;

import com.hayden.limg_diary.entity.BaseTimeEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="today_rate")
@Getter
@Setter
public class TodayRateEntity extends BaseTimeEntity {
    @Column(name="RATE_NUM")
    private int rateNum;

    @Column(name="RATE_STR")
    private String rateStr;
}
