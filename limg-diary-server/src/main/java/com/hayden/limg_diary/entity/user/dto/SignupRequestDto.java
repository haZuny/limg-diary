package com.hayden.limg_diary.entity.user.dto;

import lombok.Data;

@Data
public class SignupRequestDto {
    String username;
    String nickname;
    String password;
    String password_check;
}
