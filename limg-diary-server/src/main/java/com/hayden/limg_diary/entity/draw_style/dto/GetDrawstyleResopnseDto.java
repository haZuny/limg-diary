package com.hayden.limg_diary.entity.draw_style.dto;

import com.hayden.limg_diary.entity.DefaultResponseDto;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@Getter
@Setter
public class GetDrawstyleResopnseDto extends DefaultResponseDto {

    ArrayList<DrawStyle> data = new ArrayList<>();

    @Data
    class DrawStyle{

        public DrawStyle(String style_eng, String style_kor) {
            this.style_eng = style_eng;
            this.style_kor = style_kor;
        }

        String style_eng;
        String style_kor;
    }


    public void addData(String style_eng, String style_kor){
        this.data.add(new DrawStyle(style_eng, style_kor));
    }
}
