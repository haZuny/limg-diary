package com.hayden.limg_diary.entity.diary;

import com.hayden.limg_diary.entity.user.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;

@Repository
public interface DiaryRepository extends JpaRepository<DiaryEntity, Integer> {
    DiaryEntity findById(int diaryId);
    Optional<DiaryEntity> findByCreatedDateAndUser(LocalDate localDate, UserEntity user);
    ArrayList<DiaryEntity> findAllByUserOrderByCreatedDateDesc(UserEntity user);
    // sdate && edate
    ArrayList<DiaryEntity> findAllByCreatedDateBetweenAndUserOrderByCreatedDateDesc(LocalDate startDate, LocalDate endDate, UserEntity user);
    // sdate
    ArrayList<DiaryEntity> findAllByCreatedDateGreaterThanEqualAndUserOrderByCreatedDateDesc(LocalDate startDate, UserEntity user);
    // edate
    ArrayList<DiaryEntity> findAllByCreatedDateLessThanEqualAndUserOrderByCreatedDateDesc(LocalDate endDate, UserEntity user);
}
