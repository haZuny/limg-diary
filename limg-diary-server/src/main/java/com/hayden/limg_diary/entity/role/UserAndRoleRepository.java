package com.hayden.limg_diary.entity.role;

import com.hayden.limg_diary.entity.user.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserAndRoleRepository extends JpaRepository<UserAndRoleEntity, Integer> {
    List<UserAndRoleEntity> findAllByUser(UserEntity user);
}
