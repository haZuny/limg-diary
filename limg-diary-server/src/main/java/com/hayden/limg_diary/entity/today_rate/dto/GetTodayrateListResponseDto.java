package com.hayden.limg_diary.entity.today_rate.dto;

import com.hayden.limg_diary.entity.DefaultResponseDto;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@Getter
@Setter
public class GetTodayrateListResponseDto extends DefaultResponseDto {

    ArrayList<TodayRate> data = new ArrayList<>();

    public void addTodayList(int rate_num, String rate_str) {
        this.data.add(new TodayRate(rate_num, rate_str));
    }

    @Data
    class TodayRate {
        public TodayRate(int rate_num, String rate_str) {
            this.rate_num = rate_num;
            this.rate_str = rate_str;
        }

        int rate_num;
        String rate_str;
    }
}
