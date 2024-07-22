package com.hayden.limg_diary.entity.hashtag;

import com.hayden.limg_diary.entity.BaseTimeEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="hashtag")
@Getter
@Setter
public class HashtagEntity extends BaseTimeEntity {
    @Column(name="TAG")
    private String tag;

    @Column(name="DIARY_CNT")
    private int diary_cnt;
}
