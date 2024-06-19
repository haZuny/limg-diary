package com.hayden.limg_diary.dto;

import lombok.Getter;
import lombok.Setter;

public class DiaryDto {

    @Getter
    @Setter
    public static class CreateDiaryDto{
        String content;
        String feeling;
    }

    @Getter
    @Setter
    public static class UpdateDiaryDto{
        int diaryid;
        String content;
        String feeling;
    }
}
