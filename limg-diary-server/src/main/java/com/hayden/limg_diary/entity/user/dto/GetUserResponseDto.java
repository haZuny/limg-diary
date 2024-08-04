package com.hayden.limg_diary.entity.user.dto;

import lombok.Data;

import java.time.LocalDate;
import java.util.ArrayList;

@Data
public class GetUserResponseDto {
    int status;
    boolean success;
    String msg;
    UserSelf userSelf = new UserSelf();

    @Data
    public class UserSelf{
        int id;
        LocalDate created_date;
        LocalDate updated_date;
        ArrayList<String>  role;
        String username;
        String nickname;
    }
}
