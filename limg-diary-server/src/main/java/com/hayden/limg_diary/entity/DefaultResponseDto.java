package com.hayden.limg_diary.entity;

import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class DefaultResponseDto {
    int status;
    boolean success;
    String msg;

    public void setStatus(HttpStatus status, Boolean success, String msg){
        this.status = status.value();
        this.success = success;
        this.msg = msg;
    }
}
