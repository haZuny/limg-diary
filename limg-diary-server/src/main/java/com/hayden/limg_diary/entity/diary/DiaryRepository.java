package com.hayden.limg_diary.entity.diary;

import com.hayden.limg_diary.entity.user.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DiaryRepository extends JpaRepository<DiaryEntity, Integer> {
    List<DiaryEntity> findAllByUserOrderByCreatedDataDesc(UserEntity user);
    DiaryEntity findById(int diaryId);
}
