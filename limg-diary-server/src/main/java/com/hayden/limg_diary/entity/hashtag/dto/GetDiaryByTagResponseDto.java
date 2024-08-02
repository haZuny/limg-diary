package com.hayden.limg_diary.entity.hashtag.dto;

import com.hayden.limg_diary.entity.DefaultResponseDto;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@Getter
@Setter
public class GetDiaryByTagResponseDto extends DefaultResponseDto {

    ArrayList<Diary> data = new ArrayList<>();

    public void addData(int diary_id, String picture){
        data.add(new Diary(diary_id, picture));
    }

    @Data
    class Diary{
        public Diary(int diary_id, String picture) {
            this.diary_id = diary_id;
            this.picture = picture;
        }

        int diary_id;
        String picture;
    }
}
