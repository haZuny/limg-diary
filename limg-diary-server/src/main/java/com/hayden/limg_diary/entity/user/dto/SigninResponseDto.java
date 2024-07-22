package com.hayden.limg_diary.entity.user.dto;

import lombok.Data;
import lombok.Setter;

@Data
public class SigninResponseDto {
    int status;
    boolean success;
    String msg;

    public SigninResponseDto(int status, boolean success, String msg) {
        this.status = status;
        this.success = success;
        this.msg = msg;
    }
}
