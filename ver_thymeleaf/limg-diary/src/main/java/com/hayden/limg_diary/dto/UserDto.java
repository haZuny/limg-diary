package com.hayden.limg_diary.dto;

import lombok.Getter;
import lombok.Setter;

public class UserDto {
    @Getter
    @Setter
    public static class UserSigninDto{
        String username;
        String password;
    }

    @Getter
    @Setter
    public static class UserSignupDto{
        String username;
        String password;
        String password2;
    }
}
