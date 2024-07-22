package com.hayden.limg_diary.jwt.entity;

import com.hayden.limg_diary.jwt.entity.RefreshEntity;
import com.hayden.limg_diary.jwt.entity.RefreshRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;

@Service
public class RefreshService {
    RefreshRepository refreshRepository;

    @Autowired
    public RefreshService(RefreshRepository refreshRepository) {
        this.refreshRepository = refreshRepository;
    }

    public void addRefresh(String refresh){
        RefreshEntity refreshEntity = new RefreshEntity();
        refreshEntity.setRefresh(refresh);
        refreshRepository.save(refreshEntity);
    }
}
