package com.hayden.limg_diary.entity.diary.dto;

import lombok.Data;

import java.util.ArrayList;

@Data
public class DiaryAddRequestDto {

    private String content;
    private String draw_style;
    private ArrayList<String> hashtag;
    private int today_rate;
}
