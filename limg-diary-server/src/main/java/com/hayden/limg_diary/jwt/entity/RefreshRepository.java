package com.hayden.limg_diary.jwt.entity;

import com.hayden.limg_diary.jwt.entity.RefreshEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RefreshRepository extends JpaRepository<RefreshEntity, Integer> {
    Optional<RefreshEntity> findByRefresh(String refresh);
    boolean existsByRefresh(String refresh);
}
