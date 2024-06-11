package com.hayden.limg_diary.dto;

import lombok.Getter;
import lombok.Setter;

public class UserDto {
    @Getter
    @Setter
    public class UserSigninDto{
        String username;
        String password;
    }

    @Getter
    @Setter
    public class UserSignupDto{
        String username;
        String password;
        String password2;
    }
}
