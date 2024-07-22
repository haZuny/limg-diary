package com.hayden.limg_diary.entity.role;

import com.hayden.limg_diary.entity.BaseTimeEntity;
import com.hayden.limg_diary.entity.user.UserEntity;
import jakarta.persistence.*;

@Entity
@Table(name="user_and_role")
public class UserAndRole extends BaseTimeEntity {
    @ManyToOne
    @JoinColumn(name="USER_ID")
    UserEntity user;

    @ManyToOne
    @JoinColumn(name="ROLE_ID")
    RoleEntity role;
}
