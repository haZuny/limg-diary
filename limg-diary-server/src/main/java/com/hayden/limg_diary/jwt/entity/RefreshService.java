package com.hayden.limg_diary.jwt.entity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;

@Service
public class RefreshService {
    RefreshRepository refreshRepository;

    @Autowired
    public RefreshService(RefreshRepository refreshRepository) {
        this.refreshRepository = refreshRepository;
    }

    public void addRefresh(String refresh) {
        RefreshEntity refreshEntity = new RefreshEntity();
        refreshEntity.setRefresh(refresh);
        refreshRepository.save(refreshEntity);
    }

    public void deleteRefresh(String refresh) {
        Optional<RefreshEntity> optionalRefreshEntity = refreshRepository.findByRefresh(refresh);

        // check null
        if (optionalRefreshEntity.isEmpty()) return;

        //delete
        refreshRepository.delete(optionalRefreshEntity.get());
    }

    public boolean isExist(String refresh) {
        return refreshRepository.existsByRefresh(refresh);
    }


    @Scheduled(fixedDelay = 1000 * 60 * 60)   // 1 hours
    public void scheduledDeleteRefresh() {
        // get all refresh
        ArrayList<RefreshEntity> refreshEntities = refreshRepository.findAllByOrderByCreatedDateAsc();

        // get current date
        LocalDate current = LocalDate.now();

        Iterator<RefreshEntity> iter = refreshEntities.iterator();
        while(iter.hasNext()){
            RefreshEntity refresh = iter.next();
            LocalDate refreshDate = refresh.getCreatedDate();

            // delete refresh :: order 14 day
            if (refreshDate.until(current, ChronoUnit.DAYS) > 14){
                iter.remove();
                deleteRefresh(refresh.getRefresh());
            }
            else{
                break;
            }
        }
    }
}
