package com.hayden.limg_diary.entity.user.dto;

import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class RefreshResponseDto {
    int status;
    boolean success;
    String msg;

    public void setMember(HttpStatus status, boolean success, String msg){
        this.setStatus(status.value());
        this.setSuccess(success);
        this.setMsg(msg);
    }
}
