package com.hayden.limg_diary.entity.diary.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@Getter
@Setter
public class DiaryModifyRequestDto {
    private String content;
    private String draw_style;
    private ArrayList<String> hashtag;
    private int today_rate = -1;
}
