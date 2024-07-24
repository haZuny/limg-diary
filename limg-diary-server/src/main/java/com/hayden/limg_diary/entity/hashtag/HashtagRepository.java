package com.hayden.limg_diary.entity.hashtag;

import org.springframework.data.jpa.repository.JpaRepository;

public interface HashtagRepository extends JpaRepository<HashtagEntity,Integer> {
    HashtagEntity findByTag(String tag);
}
