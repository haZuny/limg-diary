package com.hayden.limg_diary.entity.diary.dto;

import com.hayden.limg_diary.entity.DefaultResponseDto;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Date;

@Getter
@Setter
public class DiaryIdResponseDto extends DefaultResponseDto {
    Data data = new Data();

    @lombok.Data
    public class Data{
        private int diary_id;
        private String content;
        private String picture;
        private Date created_date;
        private Date updated_date;
        ArrayList<String> hashtag;
        public void setDataValue(int diary_id, String content, String picture, Date created_date, Date updated_date, ArrayList<String> hashtag){
            this.diary_id = diary_id;
            this.content = content;
            this.created_date = created_date;
            this.updated_date = updated_date;
            this.picture = picture;
            this.hashtag = hashtag;
        }
        @lombok.Data
        public class today_rate{
            private int rate_num;
            private String rate_str;
            public void setToday_rate(int rate_num, String rate_str){
                this.rate_num = rate_num;
                this.rate_str = rate_str;
            }
        }
        @lombok.Data
        public class draw_style{
            private String style_eng;
            private String style_kor;
            public void setDraw_style(String style_eng, String style_kor){
                this.style_eng = style_eng;
                this.style_kor = style_kor;
            }
        }
    }
}
