package com.hayden.limg_diary.entity.user;

import com.hayden.limg_diary.entity.user.dto.SignupRequestDto;
import com.hayden.limg_diary.entity.user.dto.SignupResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {
    UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/signup")
    public ResponseEntity<SignupResponseDto> postSignup(@RequestBody SignupRequestDto signupRequestDto){
        boolean res = userService.signup(signupRequestDto);

        SignupResponseDto signupResponseDto = new SignupResponseDto();

        // success
        signupResponseDto.setSuccess();
        if (res) return new ResponseEntity<>(signupResponseDto, HttpStatus.CREATED);

        // fail
        signupResponseDto.setFail();
        return new ResponseEntity<>(signupResponseDto, HttpStatus.BAD_REQUEST);
    }
}
