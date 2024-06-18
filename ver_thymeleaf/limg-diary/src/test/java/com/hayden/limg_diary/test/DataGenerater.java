package com.hayden.limg_diary.test;

import com.hayden.limg_diary.dto.DiaryDto;
import com.hayden.limg_diary.dto.UserDto;
import com.hayden.limg_diary.entity.Diary;
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

    public Optional<Diary> createDiary(String content, String feels, int user_id){
        DiaryDto.CreateDiaryDto diaryDto = new DiaryDto.CreateDiaryDto();

        diaryDto.setContent(content);
        diaryDto.setFeeling(feels);

        return diaryService.createDiaryWithUserid(diaryDto, user_id);
    }
}
