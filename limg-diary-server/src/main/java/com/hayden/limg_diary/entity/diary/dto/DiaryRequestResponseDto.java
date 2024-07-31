package com.hayden.limg_diary.entity.diary.dto;

import com.hayden.limg_diary.entity.DefaultResponseDto;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@Getter
@Setter
public class DiaryRequestResponseDto extends DefaultResponseDto {
    ArrayList<DiaryTodayResponseDto> dataList = new ArrayList<>();
}
