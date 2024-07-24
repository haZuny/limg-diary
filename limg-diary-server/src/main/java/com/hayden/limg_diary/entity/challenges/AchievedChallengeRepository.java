package com.hayden.limg_diary.entity.challenges;

import com.hayden.limg_diary.entity.user.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AchievedChallengeRepository extends JpaRepository<AchievedChallengeEntity, Integer> {
    List<AchievedChallengeEntity> findAllByUser(UserEntity user);
}
