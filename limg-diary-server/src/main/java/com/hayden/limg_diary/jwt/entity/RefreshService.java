package com.hayden.limg_diary.jwt.entity;

import com.hayden.limg_diary.jwt.entity.RefreshEntity;
import com.hayden.limg_diary.jwt.entity.RefreshRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.Optional;

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

    public void deleteRefresh(String refresh){
        Optional<RefreshEntity> optionalRefreshEntity = refreshRepository.findByRefresh(refresh);

        // check null
        if(optionalRefreshEntity.isEmpty()) return;

        //delete
        refreshRepository.delete(optionalRefreshEntity.get());
    }

    public boolean isExist(String refresh){
        return refreshRepository.existsByRefresh(refresh);
    }
}
