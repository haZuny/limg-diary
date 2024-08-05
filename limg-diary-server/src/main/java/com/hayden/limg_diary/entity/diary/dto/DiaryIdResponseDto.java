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
public class DiaryIdResponseDto extends DefaultResponseDto {
    Data data = new Data();

    @lombok.Data
    public class Data{
        private int diary_id;
        private String content;
        private String picture;
        private LocalDate created_date;
        private LocalDate updated_date;
        private TodayRate today_rate = new TodayRate();
        private ArrayList<String> hashtag;
        private DrawStyle draw_style = new DrawStyle();

        @lombok.Data
        public class TodayRate{
            private int rate_num;
            private String rate_str;
        }
        @lombok.Data
        public class DrawStyle{
            private String style_eng;
            private String style_kor;
        }
    }
}
