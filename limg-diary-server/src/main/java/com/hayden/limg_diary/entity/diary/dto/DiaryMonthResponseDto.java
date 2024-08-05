package com.hayden.limg_diary.entity.diary.dto;

import com.hayden.limg_diary.entity.DefaultResponseDto;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;

@Getter
@Setter
public class DiaryMonthResponseDto extends DefaultResponseDto {
    ArrayList<Data> data = new ArrayList<>();

    @lombok.Data
    class Data{
        int diary_id;
        String picture;
        LocalDate date;

        public Data(int diary_id, String picture, LocalDate date) {
            this.diary_id = diary_id;
            this.picture = picture;
            this.date = date;
        }
    }

    public void addData(int diary_id, String picture, LocalDate date){
        data.add(new Data(diary_id, picture, date));
    }
}
