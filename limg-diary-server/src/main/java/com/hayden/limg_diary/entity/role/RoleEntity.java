package com.hayden.limg_diary.entity.role;

import com.hayden.limg_diary.entity.BaseTimeEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="role")
@Getter
@Setter
public class RoleEntity extends BaseTimeEntity {
    @Column(name = "ROLE")
    private String role;

    @Column(name = "NAME")
    private String name;

    @Column(name="LEVEL")
    private int level;
}
