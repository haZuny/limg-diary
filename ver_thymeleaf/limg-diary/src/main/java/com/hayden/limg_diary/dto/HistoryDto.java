package com.hayden.limg_diary.dto;


import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
@Setter
public class HistoryDto {
    LocalDate sdate;
    LocalDate edate;

    public String getSdateStr(){
        return sdate.format(DateTimeFormatter.ofPattern("YYYY-MM-dd"));
    }
    public String getEdateStr(){
        return edate.format(DateTimeFormatter.ofPattern("YYYY-MM-dd"));
    }

    public boolean isEmpty(){
        if (sdate == null || edate == null) return true;
        return false;
    }
}
