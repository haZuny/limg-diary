package com.hayden.limg_diary.entity.diary;

import com.hayden.limg_diary.entity.user.UserEntity;
import org.apache.catalina.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public interface DiaryRepository extends JpaRepository<DiaryEntity, Integer> {
    DiaryEntity findById(int diaryId);

    ArrayList<DiaryEntity> findAllByUserOrderByCreatedDataDesc(UserEntity user);    // 최신순
    ArrayList<DiaryEntity> findAllByUserOrderByCreatedDataAsc(UserEntity user);     // 오래된순
}
