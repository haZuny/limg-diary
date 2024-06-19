package com.hayden.limg_diary.test.repository;

import com.hayden.limg_diary.entity.Diary;
import com.hayden.limg_diary.entity.User;
import com.hayden.limg_diary.repository.DiaryRepository;
import com.hayden.limg_diary.test.DataGenerater;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@SpringBootTest
public class DiaryRepositoryTest {

    @Autowired
    DiaryRepository diaryRepository;

    @Autowired
    DataGenerater dataGenerater;

    @Test
    @Transactional
    public void findByUserAndDateBetweenTest(){
        // Given
        String username = "asbvaksdbgaskjdhgishf";
        String userpassword = "asbvaksdbgaskjdhgishf";
        Optional<User> user = dataGenerater.createUser(username, userpassword);
        String content = "안녕하세요";
        String feels = "3";
        dataGenerater.createDiary(content, feels, user.get().getUserid());

        // When
        LocalDateTime date1 = LocalDateTime.now().withHour(0).withMinute(0).withMinute(0);
        LocalDateTime date2 = LocalDateTime.now().withHour(23).withMinute(59).withMinute(59);
        Diary diary = diaryRepository.findByUseridAndDateBetween(user.get(), date1, date2);

        // Then
        Assertions.assertNotNull(diary);
        Assertions.assertEquals(content, diary.getContent());
    }

    // 일기 기간 검색 테스트
    @Test
    @Transactional
    public void findAllByUserAndDateBetweenTest(){
        // Given
        String username = "asbvaksdbgaskjdhgishf";
        String userpassword = "asbvaksdbgaskjdhgishf";
        Optional<User> user = dataGenerater.createUser(username, userpassword);
        String content1 = "안녕하세요";
        String feels1 = "3";
        dataGenerater.createDiary(content1, feels1, user.get().getUserid());

        String content2 = "치킨먹고싶닭";
        String feels2 = "5";
        LocalDateTime date = LocalDateTime.now().minusDays(1);
        dataGenerater.createDiary(content2, feels2, user.get().getUserid(), date);

        // When
        LocalDateTime date1 = LocalDateTime.now().withHour(0).withMinute(0).withMinute(0).minusDays(5);
        LocalDateTime date2 = LocalDateTime.now().withHour(23).withMinute(59).withMinute(59);
        List<Diary> diary = diaryRepository.findAllByUseridAndDateBetween(user.get(), date1, date2);

        // Then
        Assertions.assertNotNull(diary);
        Assertions.assertEquals(2, diary.size());
    }

    @Test
    @Transactional
    public void findByUserAndDateTest(){
        // Given
        String username = "asbvaksdbgaskjdhgishf";
        String userpassword = "asbvaksdbgaskjdhgishf";
        Optional<User> user = dataGenerater.createUser(username, userpassword);
        String content = "안녕하세요";
        String feels = "3";
        dataGenerater.createDiary(content, feels, user.get().getUserid());

        // When
        Diary diary = diaryRepository.findByUserAndDate(user.get(), LocalDate.now());

        // Then
        Assertions.assertNotNull(diary);
        Assertions.assertEquals(content, diary.getContent());
    }
}
