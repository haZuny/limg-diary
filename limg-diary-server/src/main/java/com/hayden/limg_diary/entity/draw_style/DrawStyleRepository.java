package com.hayden.limg_diary.entity.draw_style;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DrawStyleRepository extends JpaRepository<DrawStyleEntity, Integer> {

}
