package com.hayden.limg_diary.entity.draw_style;

import com.hayden.limg_diary.entity.BaseTimeEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="draw_style")
@Getter
@Setter
public class DrawStyleEntity extends BaseTimeEntity {
    @Column(name="STYLE_ENG")
    private String styleEng;

    @Column(name="STYLE_KOR")
    private String styleKor;
}
