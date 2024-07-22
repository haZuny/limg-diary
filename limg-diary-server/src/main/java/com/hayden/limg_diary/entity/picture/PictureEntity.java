package com.hayden.limg_diary.entity.picture;

import com.hayden.limg_diary.entity.BaseTimeEntity;
import com.hayden.limg_diary.entity.diary.DiaryEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="picture")
@Getter
@Setter
public class PictureEntity extends BaseTimeEntity {
    @Column(name="PATH")
    private String path;

    @OneToOne
    @JoinColumn(name = "DIARY_ID")
    DiaryEntity diary;
}
