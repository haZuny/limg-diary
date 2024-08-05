package com.hayden.limg_diary.entity.hashtag;

import com.hayden.limg_diary.entity.diary.DiaryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.ArrayList;

public interface DiaryAndHashtagRepository extends JpaRepository<DiaryAndHashtagEntity, Integer> {
    ArrayList<DiaryAndHashtagEntity> findAllByDiary(DiaryEntity diaryEntity);
}
