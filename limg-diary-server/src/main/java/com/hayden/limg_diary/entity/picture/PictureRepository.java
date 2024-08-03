package com.hayden.limg_diary.entity.picture;

import com.hayden.limg_diary.entity.diary.DiaryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PictureRepository extends JpaRepository<PictureEntity, Integer> {
    public PictureEntity findByDiary(DiaryEntity diary);
}
