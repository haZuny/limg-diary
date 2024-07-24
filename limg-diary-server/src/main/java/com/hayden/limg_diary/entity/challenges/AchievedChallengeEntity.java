package com.hayden.limg_diary.entity.challenges;

import com.hayden.limg_diary.entity.BaseTimeEntity;
import com.hayden.limg_diary.entity.user.UserEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="achieved_challenge")
@Getter
@Setter
public class AchievedChallengeEntity extends BaseTimeEntity {
    @ManyToOne
    @JoinColumn(name="USER_ID")
    UserEntity user;

    @ManyToOne
    @JoinColumn(name="CHALLENGES_ID")
    ChallengeEntity challenge;
}
