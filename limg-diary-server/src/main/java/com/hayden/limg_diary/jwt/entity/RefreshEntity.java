package com.hayden.limg_diary.jwt.entity;

import com.hayden.limg_diary.entity.BaseTimeEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

@Entity
@Getter
@Setter
public class RefreshEntity extends BaseTimeEntity {
    @Column(name="REFRESH", length = 1000)
    private String refresh;
}
