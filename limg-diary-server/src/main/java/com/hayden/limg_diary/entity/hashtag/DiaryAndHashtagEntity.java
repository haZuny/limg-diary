package com.hayden.limg_diary.entity.hashtag;

import com.hayden.limg_diary.entity.BaseTimeEntity;
import com.hayden.limg_diary.entity.diary.DiaryEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="diary_and_hashtag")
@Getter
@Setter
public class DiaryAndHashtagEntity extends BaseTimeEntity {
    @ManyToOne
    @JoinColumn(name="DIARY_ID")
    private DiaryEntity diary;

    @ManyToOne
    @JoinColumn(name="HASHTAG_ID")
    private HashtagEntity hashtag;
}
