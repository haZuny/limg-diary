package com.hayden.limg_diary.entity.diary.dto;

import com.hayden.limg_diary.entity.DefaultResponseDto;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;

@Getter
@Setter
public class DiarySearchResponseDto extends DefaultResponseDto {
    ArrayList<Data> data = new ArrayList<>();

    @Setter
    @Getter
    public class Data{
        int diary_id;
        String content;
        LocalDate date;

        public Data(int diary_id, String content, LocalDate date) {
            this.diary_id = diary_id;
            this.content = content;
            this.date = date;
        }
    }

    public void addData(int diary_id, String content, LocalDate date){
        this.data.add(new Data(diary_id, content, date));
    }
}
