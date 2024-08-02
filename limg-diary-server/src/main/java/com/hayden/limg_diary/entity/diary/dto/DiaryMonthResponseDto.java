package com.hayden.limg_diary.entity.diary.dto;

import com.hayden.limg_diary.entity.DefaultResponseDto;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Date;

@Getter
@Setter
public class DiaryMonthResponseDto extends DefaultResponseDto {
    ArrayList<DiaryTodayResponseDto> dataList = new ArrayList<>();
}
