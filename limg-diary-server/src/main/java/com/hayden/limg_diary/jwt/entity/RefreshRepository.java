package com.hayden.limg_diary.jwt.entity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Optional;

@Repository
public interface RefreshRepository extends JpaRepository<RefreshEntity, Integer> {
    Optional<RefreshEntity> findByRefresh(String refresh);

    ArrayList<RefreshEntity> findAllByOrderByCreatedDateAsc();   // oldest
    boolean existsByRefresh(String refresh);
}
