package com.hayden.limg_diary.entity.diary;

import com.hayden.limg_diary.entity.BaseTimeEntity;
import com.hayden.limg_diary.entity.user.UserEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="diary")
@Getter
@Setter
public class DiaryEntity extends BaseTimeEntity {
    @Column(name="CONTENT", length = 1500)
    private String content;

    @ManyToOne
    @JoinColumn(name="USER_ID")
    UserEntity user;
}
