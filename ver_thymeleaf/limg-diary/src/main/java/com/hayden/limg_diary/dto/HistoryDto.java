package com.hayden.limg_diary.dto;


import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class HistoryDto {
    LocalDate sdate;
    LocalDate edate;
}
