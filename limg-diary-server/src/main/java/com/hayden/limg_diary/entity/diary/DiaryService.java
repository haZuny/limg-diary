package com.hayden.limg_diary.entity.diary;

import com.hayden.limg_diary.entity.diary.dto.DiaryAddRequestDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DiaryService {
    DiaryRepository diaryRepository;
    Today_rateService today_rateService;
    @Autowired
    public DiaryService(DiaryRepository diaryRepository, Today_rateService today_rateService) {
        this.diaryRepository = diaryRepository;
        this.today_rateService = today_rateService;
    }

    public boolean diaryAdd(DiaryAddRequestDto diaryAddRequestDto){
        if(diaryAddRequestDto.getContent()==null) return false;
        if(diaryAddRequestDto.getDraw_style()==null) return false;

        DiaryEntity diaryEntity = new DiaryEntity();
        diaryEntity.setContent(diaryAddRequestDto.getContent());
        diaryRepository.save(diaryEntity);

        today_rateService.today_rateAdd(diaryAddRequestDto.getToday_rate());

    }
}
