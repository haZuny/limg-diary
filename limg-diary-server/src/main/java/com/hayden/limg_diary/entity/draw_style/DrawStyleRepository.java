package com.hayden.limg_diary.entity.draw_style;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DrawStyleRepository extends JpaRepository<DrawStyleEntity, Integer> {
    Optional<DrawStyleEntity> findByStyleEng(String styleEng);
}
