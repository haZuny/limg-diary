package com.hayden.limg_diary.entity.diary.dto;

import com.hayden.limg_diary.entity.DefaultResponseDto;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.util.Date;

@Getter
@Setter
public class DiaryTodayResponseDto extends DefaultResponseDto {
    Data data = new Data();
    @Getter
    @Setter
    public class Data{
        private int diary_id;
        private String picture;
        private Date today;
        public void setDataValue(int id, String picture, Date today){
            this.diary_id = id;
            this.picture = picture;
            this.today = today;
        }
    }
}
