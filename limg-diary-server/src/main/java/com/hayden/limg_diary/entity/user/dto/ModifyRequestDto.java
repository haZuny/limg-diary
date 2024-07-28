package com.hayden.limg_diary.entity.user.dto;

import lombok.Data;

@Data
public class ModifyRequestDto {
    String nickname;
    String new_password;
    String new_password_check;
    String password;
}
