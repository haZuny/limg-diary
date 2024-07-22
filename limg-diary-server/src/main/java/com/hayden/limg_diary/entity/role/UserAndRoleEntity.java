package com.hayden.limg_diary.entity.role;

import com.hayden.limg_diary.entity.BaseTimeEntity;
import com.hayden.limg_diary.entity.user.UserEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="user_and_role")
@Getter
@Setter
public class UserAndRoleEntity extends BaseTimeEntity {
    @ManyToOne
    @JoinColumn(name="USER_ID")
    UserEntity user;

    @ManyToOne
    @JoinColumn(name="ROLE_ID")
    RoleEntity role;
}
