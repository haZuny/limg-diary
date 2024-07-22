package com.hayden.limg_diary.entity.draw_style;

import com.hayden.limg_diary.entity.BaseTimeEntity;
import com.hayden.limg_diary.entity.picture.PictureEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="picture_and_style")
@Getter
@Setter
public class PictureAndStyleEntity extends BaseTimeEntity {
    @ManyToOne
    @JoinColumn(name="STYLE_ID")
    DrawStyleEntity drawStyle;

    @OneToOne
    @JoinColumn(name="PICTURE_ID")
    PictureEntity picture;
}
