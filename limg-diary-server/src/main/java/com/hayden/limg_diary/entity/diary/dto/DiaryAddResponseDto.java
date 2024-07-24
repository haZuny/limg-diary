package com.hayden.limg_diary.entity.diary.dto;

import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class DiaryAddResponseDto {

    private int status;
    private boolean success;
    private String msg;

    //성공
    public void setSuccess(){
        this.status = HttpStatus.CREATED.value();
        this.success = true;
        this.msg = "success";
    }

    //실패
    public void setFail(){
        this.status = HttpStatus.BAD_REQUEST.value();
        this.success = false;
        this.msg = "fail";
    }
}
