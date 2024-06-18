package com.hayden.limg_diary.test.service;

import com.hayden.limg_diary.dto.UserDto;
import com.hayden.limg_diary.entity.User;
import com.hayden.limg_diary.repository.UserRepository;
import com.hayden.limg_diary.service.DiaryService;
import com.hayden.limg_diary.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class DataGenerater {
    @Autowired
    UserService userService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    DiaryService diaryService;

    public Optional<User> createUser(String username, String password){
        UserDto.UserSignupDto userDto = new UserDto.UserSignupDto();
        userDto.setUsername(username);
        userDto.setPassword(password);
        userDto.setPassword2(password);

        try{
            if (userService.signUp(userDto)){
                return Optional.of(userRepository.findByUsername(username));
            }
            throw new IllegalArgumentException("this username is exists");
        } catch (Exception e){
            e.printStackTrace();
            return Optional.empty();
        }
    }
}
