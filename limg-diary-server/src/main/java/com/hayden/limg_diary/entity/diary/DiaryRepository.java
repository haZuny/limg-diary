package com.hayden.limg_diary.entity.diary;

import com.hayden.limg_diary.entity.user.UserEntity;
import org.springframework.cglib.core.Local;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;

@Repository
public interface DiaryRepository extends JpaRepository<DiaryEntity, Integer> {
    DiaryEntity findById(int diaryId);
    Optional<DiaryEntity> findByCreatedDateAndUser(LocalDate localDate, UserEntity user);
    ArrayList<DiaryEntity> findAllByCreatedDateBetweenAndUser(LocalDate startDate, LocalDate endDate, UserEntity user);

    ArrayList<DiaryEntity> findAllByUserOrderByCreatedDateDesc(UserEntity user);    // 최신순
    ArrayList<DiaryEntity> findAllByUserOrderByCreatedDateAsc(UserEntity user);     // 오래된순

}
