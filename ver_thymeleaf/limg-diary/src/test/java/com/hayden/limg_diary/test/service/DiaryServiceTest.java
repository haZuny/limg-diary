package com.hayden.limg_diary.test.service;

import com.hayden.limg_diary.dto.DiaryDto;
import com.hayden.limg_diary.dto.UserDto;
import com.hayden.limg_diary.entity.Diary;
import com.hayden.limg_diary.repository.DiaryRepository;
import com.hayden.limg_diary.service.DiaryService;
import com.hayden.limg_diary.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@SpringBootTest
public class DiaryServiceTest {

    @Autowired
    DiaryService diaryService;

    @Autowired
    DiaryRepository diaryRepository;

    @Test
    @Transactional
    public void saveTest(){

        // When
        DiaryDto.CreateDiaryDto createDiaryDto = new DiaryDto.CreateDiaryDto();
        createDiaryDto.setContent("안녕하세요");
        createDiaryDto.setFeeling("상");
        Optional<Diary> diaryOp = diaryService.createDiary(createDiaryDto, 2);

        // Then
        Assertions.assertNotNull(diaryOp.orElse(null));
        Diary diary = diaryOp.get();
        Assertions.assertNotEquals(diary.getDiary_id(), "null");
        Assertions.assertNotEquals(diary.getDate(), "null");
        Assertions.assertNotEquals(diary.getFeeling(), "null");
        Assertions.assertNotEquals(diary.getImage_path(), "null");
    }
}
