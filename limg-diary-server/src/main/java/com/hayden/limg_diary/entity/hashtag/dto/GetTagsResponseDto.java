package com.hayden.limg_diary.entity.hashtag.dto;

import com.hayden.limg_diary.entity.DefaultResponseDto;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@Getter
@Setter
public class GetTagsResponseDto extends DefaultResponseDto {

    ArrayList<String> data = new ArrayList<>();
}
