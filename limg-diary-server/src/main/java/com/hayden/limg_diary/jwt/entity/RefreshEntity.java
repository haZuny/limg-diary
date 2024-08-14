package com.hayden.limg_diary.jwt.entity;

import com.hayden.limg_diary.entity.BaseTimeEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

@Getter
@Setter
@Entity(name = "refresh")
public class RefreshEntity extends BaseTimeEntity {
    @Column(name="REFRESH", length = 700, unique = true)
    private String refresh;
}
