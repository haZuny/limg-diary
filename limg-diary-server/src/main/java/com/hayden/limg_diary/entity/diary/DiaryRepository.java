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
    Optional<DiaryEntity> findByCreatedDate(LocalDate localDate);

    ArrayList<DiaryEntity> findAllByUserOrderByCreatedDateDesc(UserEntity user);    // 최신순
    ArrayList<DiaryEntity> findAllByUserOrderByCreatedDateAsc(UserEntity user);     // 오래된순

    ArrayList<DiaryEntity> findAllByCreatedDateBetween(Date sdate, Date edate);
}
