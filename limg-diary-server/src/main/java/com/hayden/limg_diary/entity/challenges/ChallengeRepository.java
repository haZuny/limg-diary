package com.hayden.limg_diary.entity.challenges;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChallengeRepository extends JpaRepository<ChallengeEntity, Integer> {

}
