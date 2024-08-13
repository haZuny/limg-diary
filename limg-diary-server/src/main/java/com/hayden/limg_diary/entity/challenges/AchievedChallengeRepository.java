package com.hayden.limg_diary.entity.challenges;

import com.hayden.limg_diary.entity.user.UserEntity;
import org.apache.catalina.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public interface AchievedChallengeRepository extends JpaRepository<AchievedChallengeEntity, Integer> {
    ArrayList<AchievedChallengeEntity> findAllByUser(UserEntity user);

    Optional<AchievedChallengeEntity> findByUserAndChallenge(UserEntity user, ChallengeEntity challenge);

}
