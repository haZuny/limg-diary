package com.hayden.limg_diary.entity.user.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.Date;

@Data
public class GetUserResponseDto {
    int status;
    boolean success;
    String msg;
    UserSelf userSelf = new UserSelf();

    @Data
    public class UserSelf{
        int id;
        Date created_date;
        Date updated_date;
        ArrayList<String>  role;
        String username;
        String nickname;
    }
}
