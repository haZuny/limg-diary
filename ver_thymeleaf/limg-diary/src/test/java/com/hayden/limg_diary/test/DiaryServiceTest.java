package com.hayden.limg_diary.test;

import com.hayden.limg_diary.dto.DiaryDto;
import com.hayden.limg_diary.dto.UserDto;
import com.hayden.limg_diary.entity.Diary;
import com.hayden.limg_diary.entity.User;
import com.hayden.limg_diary.repository.DiaryRepository;
import com.hayden.limg_diary.service.DiaryService;
import com.hayden.limg_diary.service.UserService;
import com.hayden.limg_diary.test.service.DataGenerater;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@SpringBootTest
public class DiaryServiceTest {

    @Autowired
    DataGenerater dataGenerater;

    @Autowired
    DiaryService diaryService;
    @Autowired
    UserService userService;

    @Autowired
    DiaryRepository diaryRepository;


    @Test
    @Transactional
    public void saveTest(){

        // Given
        String username = "asgsabkasl123h65616saa";
        String password = "asgsabkasl123h65616saa";
        User user = dataGenerater.createUser(username, password).get();

        // When
        DiaryDto.CreateDiaryDto createDiaryDto = new DiaryDto.CreateDiaryDto();
        createDiaryDto.setContent("안녕하세요");
        createDiaryDto.setFeeling("상");
        Optional<Diary> diaryOp = diaryService.createDiaryWithUserid(createDiaryDto, user.getUser_id());

        // Then
        Assertions.assertNotNull(diaryOp.orElse(null));
        Diary diary = diaryOp.get();
        Assertions.assertNotEquals(diary.getDiary_id(), "null");
        Assertions.assertNotEquals(diary.getDate(), "null");
        Assertions.assertNotEquals(diary.getFeeling(), "null");
        Assertions.assertNotEquals(diary.getImage_path(), "null");
    }
}
