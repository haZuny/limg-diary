package com.hayden.limg_diary.entity.challenges;

import com.hayden.limg_diary.entity.BaseTimeEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="challenges")
@Getter
@Setter
public class ChallengeEntity extends BaseTimeEntity {
    @Column(name = "NAME", unique = true)
    private String name;

    @Column(name = "SPECIFIC")
    private String specific;

    @Column(name="ACHIEVED_ICON_PATH")
    private String achievedIconPath;

    @Column(name="UNACHIEVED_ICON_PATH")
    private String unachievedIconPath;
}
