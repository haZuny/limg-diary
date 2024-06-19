package com.hayden.limg_diary.repository;

import com.hayden.limg_diary.entity.Diary;
import com.hayden.limg_diary.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface DiaryRepository extends JpaRepository<Diary, Integer> {
    Diary findByUseridAndDateBetween(User user, LocalDateTime date1, LocalDateTime date2);
    List<Diary> findAllByUseridAndDateBetween(User user, LocalDateTime date1, LocalDateTime date2);

    default Diary findByUserAndDate(User user, LocalDate date){
        LocalDateTime date1 = LocalDateTime.of(date, LocalTime.of(0,0,0));
        LocalDateTime date2 = LocalDateTime.of(date, LocalTime.of(23,59,59));
        return findByUseridAndDateBetween(user, date1, date2);
    }
}
